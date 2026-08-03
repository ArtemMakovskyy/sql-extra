package com.sql.sqlextra.config;

import com.sql.sqlextra.dto.*;
import com.sql.sqlextra.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    @Value("${seeder.products:50}")
    private int productsCount;

    @Value("${seeder.sessions:200}")
    private int sessionsCount;

    @Value("${seeder.accounts:100}")
    private int accountsCount;

    @Value("${seeder.parallelism:6}")
    private int parallelism;

    private final ProductService productService;
    private final SessionService sessionService;
    private final SessionParamsService sessionParamsService;
    private final AccountService accountService;
    private final AccountSessionService accountSessionService;
    private final OrderService orderService;
    private final AbTestService abTestService;
    private final EventParamsService eventParamsService;
    private final EmailSentService emailSentService;
    private final EmailOpenService emailOpenService;
    private final EmailVisitService emailVisitService;
    private final PaidSearchCostService paidSearchCostService;
    private final RevenuePredictService revenuePredictService;

    private final ThreadLocal<Faker> faker = ThreadLocal.withInitial(() -> new Faker(new Locale("en")));

    @Override
    public void run(String... args) {
        if (productService.count() > 0) {
            log.info("Database already contains data, skipping seeding");
            return;
        }

        log.info("Starting data seeding...");

        AtomicBoolean seedingInProgress = new AtomicBoolean(true);
        long startTime = System.currentTimeMillis();
        Thread timerThread = new Thread(() -> {
            while (seedingInProgress.get()) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                if (seedingInProgress.get()) {
                    printElapsed(startTime);
                }
            }
        });
        timerThread.setDaemon(true);
        timerThread.start();

        ExecutorService executor = null;
        try {
            executor = Executors.newFixedThreadPool(parallelism);
            final ExecutorService ex = executor;

            CompletableFuture<List<ProductDTO>> productsFuture =
                    CompletableFuture.supplyAsync(this::seedProducts, ex);
            CompletableFuture<List<SessionDTO>> sessionsFuture =
                    CompletableFuture.supplyAsync(this::seedSessions, ex);
            CompletableFuture<List<AccountDTO>> accountsFuture =
                    CompletableFuture.supplyAsync(this::seedAccounts, ex);
            CompletableFuture<Void> paidSearchFuture =
                    CompletableFuture.runAsync(this::seedPaidSearchCost, ex);
            CompletableFuture<Void> revenuePredictFuture =
                    CompletableFuture.runAsync(this::seedRevenuePredict, ex);

            CompletableFuture<Void> sessionParamsFuture =
                    sessionsFuture.thenAcceptAsync(this::seedSessionParams, ex);
            CompletableFuture<Void> eventsFuture =
                    sessionsFuture.thenAcceptAsync(this::seedEventParams, ex);
            CompletableFuture<Void> abTestsFuture =
                    sessionsFuture.thenAcceptAsync(this::seedAbTests, ex);

            CompletableFuture<Void> ordersFuture =
                    sessionsFuture.thenAcceptBothAsync(productsFuture, this::seedOrders, ex);
            CompletableFuture<Void> accountSessionsFuture =
                    accountsFuture.thenAcceptBothAsync(sessionsFuture, this::seedAccountSessions, ex);
            CompletableFuture<Void> emailsFuture =
                    accountsFuture.thenAcceptBothAsync(sessionsFuture, this::seedEmails, ex);

            CompletableFuture.allOf(
                    paidSearchFuture, revenuePredictFuture,
                    sessionParamsFuture, eventsFuture, abTestsFuture,
                    ordersFuture, accountSessionsFuture, emailsFuture
            ).join();
        } finally {
            if (executor != null) {
                executor.shutdownNow();
            }
            seedingInProgress.set(false);
            try {
                timerThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        log.info("Data seeding completed!");
    }

    private void printElapsed(long startTime) {
        long elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000;
        long minutes = elapsedSeconds / 60;
        long seconds = elapsedSeconds % 60;
        String time = (minutes > 0)
                ? String.format("%d minute%s %02d second%s", minutes, minutes == 1 ? "" : "s", seconds, seconds == 1 ? "" : "s")
                : String.format("%d second%s", seconds, seconds == 1 ? "" : "s");
        System.out.println("Please wait, filling data with information, " + time + " have passed");
        System.out.flush();
    }

    private List<ProductDTO> seedProducts() {
        List<ProductDTO> products = new ArrayList<>();
        String[] categories = {
                "Beds",
                "Bookcases & shelving units",
                "Chairs",
                "Tables",
                "Sofas",
                "Electronics",
                "Furniture",
                "Clothing",
                "Books",
                "Home & Garden"
        };

        for (int i = 1; i <= productsCount; i++) {
            ProductDTO product = new ProductDTO(
                    (long) i,
                    faker.get().commerce().productName(),
                    categories[ThreadLocalRandom.current().nextInt(categories.length)],
                    new BigDecimal(faker.get().commerce().price().replace(",", "")),
                    faker.get().lorem().sentence(10) + (ThreadLocalRandom.current().nextBoolean() ? " 120x60x80 cm" : "")
            );
            products.add(product);
        }

        return productService.saveAll(products);
    }

    private List<SessionDTO> seedSessions() {
        List<SessionDTO> sessions = new ArrayList<>();
        LocalDate startDate = LocalDate.now().minusMonths(3);

        for (int i = 0; i < sessionsCount; i++) {
            SessionDTO session = new SessionDTO();
            session.setGaSessionId("ga_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16));
            session.setDate(startDate.plusDays(ThreadLocalRandom.current().nextInt(90)));
            sessions.add(session);
        }

        return sessionService.saveAll(sessions);
    }

    private void seedSessionParams(List<SessionDTO> sessions) {
        List<SessionParamsDTO> paramsList = new ArrayList<>();
        String[] devices = {"desktop", "mobile", "tablet"};
        String[] browsers = {"Chrome", "Firefox", "Safari", "Edge"};
        String[] operatingSystems = {"Windows", "macOS", "Linux", "Android", "iOS"};
        Map<String, String[]> continentsToCountries = new LinkedHashMap<>();
        continentsToCountries.put("Europe", new String[]{"United Kingdom", "Germany", "France", "Italy", "Spain", "Netherlands"});
        continentsToCountries.put("North America", new String[]{"United States", "Canada", "Mexico"});
        continentsToCountries.put("Asia", new String[]{"Japan", "India", "China", "South Korea"});
        continentsToCountries.put("South America", new String[]{"Brazil"});
        continentsToCountries.put("Oceania", new String[]{"Australia"});
        continentsToCountries.put("Africa", new String[]{"South Africa", "Egypt", "Nigeria", "Kenya"});
        String[] continentNames = continentsToCountries.keySet().toArray(new String[0]);
        String[] mediums = {"organic", "paid", "social", "email", "referral"};
        String[] channels = {"Organic Search", "Paid Search", "Social", "Direct", "Referral"};

        for (SessionDTO session : sessions) {
            SessionParamsDTO params = new SessionParamsDTO();
            params.setGaSessionId(session.getGaSessionId());
            params.setDevice(devices[ThreadLocalRandom.current().nextInt(devices.length)]);
            params.setBrowser(browsers[ThreadLocalRandom.current().nextInt(browsers.length)]);
            params.setOperatingSystem(operatingSystems[ThreadLocalRandom.current().nextInt(operatingSystems.length)]);

            if (ThreadLocalRandom.current().nextInt(10) < 3) {
                params.setLanguage(null);
            } else if (ThreadLocalRandom.current().nextInt(10) < 5) {
                params.setLanguage(faker.get().nation().language() + "-" + faker.get().nation().nationality());
            } else {
                params.setLanguage("en-" + faker.get().address().countryCode());
            }

            String continent = continentNames[ThreadLocalRandom.current().nextInt(continentNames.length)];
            params.setContinent(continent);
            String[] continentCountries = continentsToCountries.get(continent);
            params.setCountry(continentCountries[ThreadLocalRandom.current().nextInt(continentCountries.length)]);
            params.setMedium(mediums[ThreadLocalRandom.current().nextInt(mediums.length)]);
            params.setChannel(channels[ThreadLocalRandom.current().nextInt(channels.length)]);
            if (ThreadLocalRandom.current().nextBoolean()) {
                params.setMobileModelName(faker.get().phoneNumber().cellPhone());
            }
            paramsList.add(params);
        }

        sessionParamsService.saveAll(paramsList);
    }

    private List<AccountDTO> seedAccounts() {
        List<AccountDTO> accounts = new ArrayList<>();

        for (int i = 0; i < accountsCount; i++) {
            AccountDTO account = new AccountDTO();
            account.setSendInterval(7 + ThreadLocalRandom.current().nextInt(21));
            account.setIsVerified(ThreadLocalRandom.current().nextInt(100) < 70 ? 1 : 0);
            account.setIsUnsubscribed(ThreadLocalRandom.current().nextInt(100) < 30 ? 1 : 0);
            accounts.add(account);
        }

        return accountService.saveAll(accounts);
    }

    private void seedAccountSessions(List<AccountDTO> accounts, List<SessionDTO> sessions) {
        List<AccountSessionDTO> accountSessions = new ArrayList<>();
        List<SessionDTO> usedSessions = new ArrayList<>();

        for (AccountDTO account : accounts) {
            int numSessions = ThreadLocalRandom.current().nextInt(3) + 1;
            for (int i = 0; i < numSessions; i++) {
                SessionDTO session = sessions.get(ThreadLocalRandom.current().nextInt(sessions.size()));
                if (!usedSessions.contains(session)) {
                    usedSessions.add(session);
                    AccountSessionDTO as = new AccountSessionDTO();
                    as.setAccountId(account.getId());
                    as.setGaSessionId(session.getGaSessionId());
                    accountSessions.add(as);
                }
            }
        }

        accountSessionService.saveAll(accountSessions);
    }

    private void seedOrders(List<SessionDTO> sessions, List<ProductDTO> products) {
        List<OrderDTO> orders = new ArrayList<>();

        for (SessionDTO session : sessions) {
            int numOrders = ThreadLocalRandom.current().nextInt(4);
            for (int i = 0; i < numOrders; i++) {
                OrderDTO order = new OrderDTO();
                order.setGaSessionId(session.getGaSessionId());
                order.setItemId(products.get(ThreadLocalRandom.current().nextInt(products.size())).getItemId());
                orders.add(order);
            }
        }

        orderService.saveAll(orders);
    }

    private void seedAbTests(List<SessionDTO> sessions) {
        List<AbTestDTO> abTests = new ArrayList<>();

        for (SessionDTO session : sessions) {
            if (ThreadLocalRandom.current().nextInt(10) < 3) {
                AbTestDTO abTest = new AbTestDTO();
                abTest.setGaSessionId(session.getGaSessionId());
                abTest.setTest(ThreadLocalRandom.current().nextInt(5) + 1);
                abTest.setTestGroup(ThreadLocalRandom.current().nextInt(2) + 1);
                abTests.add(abTest);
            }
        }

        abTestService.saveAll(abTests);
    }

    private void seedEventParams(List<SessionDTO> sessions) {
        List<EventParamsDTO> events = new ArrayList<>();
        String[] eventNames = {"page_view", "scroll", "add_to_cart", "remove_from_cart", "checkout", "purchase", "sign_up", "login", "user_engagement", "click"};

        for (SessionDTO session : sessions) {
            LocalDateTime baseTimestamp = LocalDateTime.now();
            int numEvents = ThreadLocalRandom.current().nextInt(10) + 1;
            for (int i = 0; i < numEvents; i++) {
                EventParamsDTO event = new EventParamsDTO();
                event.setGaSessionId(session.getGaSessionId());
                event.setEventDate(session.getDate());
                event.setEventTimestamp(baseTimestamp.minusDays(ThreadLocalRandom.current().nextInt(30)).plusNanos(i));
                event.setEventName(eventNames[ThreadLocalRandom.current().nextInt(eventNames.length)]);
                event.setEventParams("{\"key\": \"" + faker.get().lorem().word() + "\"}");
                events.add(event);
            }
        }

        eventParamsService.saveAll(events);
    }

    private void seedEmails(List<AccountDTO> accounts, List<SessionDTO> sessions) {
        List<EmailSentDTO> emailSents = new ArrayList<>();
        List<EmailOpenDTO> emailOpens = new ArrayList<>();
        List<EmailVisitDTO> emailVisits = new ArrayList<>();

        for (AccountDTO account : accounts) {
            int numEmails = ThreadLocalRandom.current().nextInt(5) + 1;
            for (int i = 0; i < numEmails; i++) {
                EmailSentDTO sent = new EmailSentDTO();
                sent.setIdAccount(account.getId());
                sent.setSentDate(ThreadLocalRandom.current().nextInt(30));
                sent.setLetterType(ThreadLocalRandom.current().nextInt(5) + 1);
                sent.setIdMessage("msg_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
                emailSents.add(sent);

                if (ThreadLocalRandom.current().nextBoolean()) {
                    EmailOpenDTO open = new EmailOpenDTO();
                    open.setIdAccount(account.getId());
                    open.setOpenDate(sent.getSentDate() + ThreadLocalRandom.current().nextInt(5));
                    open.setLetterType(sent.getLetterType());
                    open.setIdMessage(sent.getIdMessage());
                    emailOpens.add(open);

                    if (ThreadLocalRandom.current().nextBoolean()) {
                        EmailVisitDTO visit = new EmailVisitDTO();
                        visit.setIdAccount(account.getId());
                        visit.setVisitDate(open.getOpenDate() + ThreadLocalRandom.current().nextInt(3));
                        visit.setLetterType(sent.getLetterType());
                        visit.setIdMessage(sent.getIdMessage());
                        emailVisits.add(visit);
                    }
                }
            }
        }

        emailSentService.saveAll(emailSents);
        emailOpenService.saveAll(emailOpens);
        emailVisitService.saveAll(emailVisits);
    }

    private void seedPaidSearchCost() {
        List<PaidSearchCostDTO> costs = new ArrayList<>();
        LocalDate startDate = LocalDate.now().minusMonths(3);

        for (int i = 0; i < 90; i++) {
            PaidSearchCostDTO cost = new PaidSearchCostDTO();
            cost.setDate(startDate.plusDays(i));
            cost.setCost(new BigDecimal(ThreadLocalRandom.current().nextInt(500) + 50));
            costs.add(cost);
        }

        paidSearchCostService.saveAll(costs);
    }

    private void seedRevenuePredict() {
        List<RevenuePredictDTO> predicts = new ArrayList<>();
        LocalDate startDate = LocalDate.now().minusMonths(3);

        for (int i = 0; i < 90; i++) {
            RevenuePredictDTO predict = new RevenuePredictDTO();
            predict.setDate(startDate.plusDays(i));
            predict.setPredict(new BigDecimal(ThreadLocalRandom.current().nextInt(1000) + 200));
            predicts.add(predict);
        }

        revenuePredictService.saveAll(predicts);
    }
}