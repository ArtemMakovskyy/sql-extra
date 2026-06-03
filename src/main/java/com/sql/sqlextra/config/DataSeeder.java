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
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

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

    private final Faker faker = new Faker(new Locale("en"));
    private final Random random = new Random();

    @Override
    public void run(String... args) {
        if (productService.count() > 0) {
            log.info("Database already contains data, skipping seeding");
            return;
        }

        log.info("Starting data seeding...");

        List<ProductDTO> products = seedProducts();
        List<SessionDTO> sessions = seedSessions();
        List<SessionParamsDTO> sessionParams = seedSessionParams(sessions);
        List<AccountDTO> accounts = seedAccounts();
        seedAccountSessions(accounts, sessions);
        seedOrders(sessions, products);
        seedAbTests(sessions);
        seedEventParams(sessions);
        seedEmails(accounts, sessions);
        seedPaidSearchCost();
        seedRevenuePredict();

        log.info("Data seeding completed!");
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
                    faker.commerce().productName(),
                    categories[random.nextInt(categories.length)],
                    new BigDecimal(faker.commerce().price().replace(",", "")),
                    faker.lorem().sentence(10) + (random.nextBoolean() ? " 120x60x80 cm" : "")
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
            session.setDate(startDate.plusDays(random.nextInt(90)));
            sessions.add(session);
        }

        return sessionService.saveAll(sessions);
    }

    private List<SessionParamsDTO> seedSessionParams(List<SessionDTO> sessions) {
        List<SessionParamsDTO> paramsList = new ArrayList<>();
        String[] devices = {"desktop", "mobile", "tablet"};
        String[] browsers = {"Chrome", "Firefox", "Safari", "Edge"};
        String[] operatingSystems = {"Windows", "macOS", "Linux", "Android", "iOS"};
        String[] continents = {"Europe", "North America", "Asia", "South America", "Africa", "Oceania"};
        String[] countries = {
            "United States", "United Kingdom", "Germany", "France", "Canada",
            "Australia", "Japan", "Italy", "Spain", "Netherlands", "Brazil",
            "India", "China", "South Korea", "Mexico"
        };
        String[] mediums = {"organic", "paid", "social", "email", "referral"};
        String[] channels = {"Organic Search", "Paid Search", "Social", "Direct", "Referral"};

        for (SessionDTO session : sessions) {
            SessionParamsDTO params = new SessionParamsDTO();
            params.setGaSessionId(session.getGaSessionId());
            params.setDevice(devices[random.nextInt(devices.length)]);
            params.setBrowser(browsers[random.nextInt(browsers.length)]);
            params.setOperatingSystem(operatingSystems[random.nextInt(operatingSystems.length)]);

            if (random.nextInt(10) < 3) {
                params.setLanguage(null);
            } else if (random.nextInt(10) < 5) {
                params.setLanguage(faker.nation().language() + "-" + faker.nation().nationality());
            } else {
                params.setLanguage("en-" + faker.address().countryCode());
            }

            params.setContinent(continents[random.nextInt(continents.length)]);
            params.setCountry(countries[random.nextInt(countries.length)]);
            params.setMedium(mediums[random.nextInt(mediums.length)]);
            params.setChannel(channels[random.nextInt(channels.length)]);
            if (random.nextBoolean()) {
                params.setMobileModelName(faker.phoneNumber().cellPhone());
            }
            paramsList.add(params);
        }

        return sessionParamsService.saveAll(paramsList);
    }

    private List<AccountDTO> seedAccounts() {
        List<AccountDTO> accounts = new ArrayList<>();

        for (int i = 0; i < accountsCount; i++) {
            AccountDTO account = new AccountDTO();
            account.setSendInterval(7 + random.nextInt(21));
            account.setIsVerified(random.nextInt(100) < 70 ? 1 : 0);
            account.setIsUnsubscribed(random.nextInt(100) < 30 ? 1 : 0);
            accounts.add(account);
        }

        return accountService.saveAll(accounts);
    }

    private void seedAccountSessions(List<AccountDTO> accounts, List<SessionDTO> sessions) {
        List<AccountSessionDTO> accountSessions = new ArrayList<>();
        List<SessionDTO> usedSessions = new ArrayList<>();

        for (AccountDTO account : accounts) {
            int numSessions = random.nextInt(3) + 1;
            for (int i = 0; i < numSessions; i++) {
                SessionDTO session = sessions.get(random.nextInt(sessions.size()));
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
            int numOrders = random.nextInt(4);
            for (int i = 0; i < numOrders; i++) {
                OrderDTO order = new OrderDTO();
                order.setGaSessionId(session.getGaSessionId());
                order.setItemId(products.get(random.nextInt(products.size())).getItemId());
                orders.add(order);
            }
        }

        orderService.saveAll(orders);
    }

    private void seedAbTests(List<SessionDTO> sessions) {
        List<AbTestDTO> abTests = new ArrayList<>();

        for (SessionDTO session : sessions) {
            if (random.nextInt(10) < 3) {
                AbTestDTO abTest = new AbTestDTO();
                abTest.setGaSessionId(session.getGaSessionId());
                abTest.setTest(random.nextInt(5) + 1);
                abTest.setTestGroup(random.nextInt(2) + 1);
                abTests.add(abTest);
            }
        }

        abTestService.saveAll(abTests);
    }

    private void seedEventParams(List<SessionDTO> sessions) {
        List<EventParamsDTO> events = new ArrayList<>();
        String[] eventNames = {"page_view", "scroll", "add_to_cart", "remove_from_cart", "checkout", "purchase", "sign_up", "login", "user_engagement", "click"};

        for (SessionDTO session : sessions) {
            int numEvents = random.nextInt(10) + 1;
            for (int i = 0; i < numEvents; i++) {
                EventParamsDTO event = new EventParamsDTO();
                event.setGaSessionId(session.getGaSessionId());
                event.setEventDate(session.getDate());
                event.setEventTimestamp(LocalDateTime.now().minusDays(random.nextInt(30)));
                event.setEventName(eventNames[random.nextInt(eventNames.length)]);
                event.setEventParams("{\"key\": \"" + faker.lorem().word() + "\"}");
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
            int numEmails = random.nextInt(5) + 1;
            for (int i = 0; i < numEmails; i++) {
                EmailSentDTO sent = new EmailSentDTO();
                sent.setIdAccount(account.getId());
                sent.setSentDate(random.nextInt(30));
                sent.setLetterType(random.nextInt(5) + 1);
                sent.setIdMessage("msg_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
                emailSents.add(sent);

                if (random.nextBoolean()) {
                    EmailOpenDTO open = new EmailOpenDTO();
                    open.setIdAccount(account.getId());
                    open.setOpenDate(sent.getSentDate() + random.nextInt(5));
                    open.setLetterType(sent.getLetterType());
                    open.setIdMessage(sent.getIdMessage());
                    emailOpens.add(open);

                    if (random.nextBoolean()) {
                        EmailVisitDTO visit = new EmailVisitDTO();
                        visit.setIdAccount(account.getId());
                        visit.setVisitDate(open.getOpenDate() + random.nextInt(3));
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
            cost.setCost(new BigDecimal(random.nextInt(500) + 50));
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
            predict.setPredict(new BigDecimal(random.nextInt(1000) + 200));
            predicts.add(predict);
        }

        revenuePredictService.saveAll(predicts);
    }
}
