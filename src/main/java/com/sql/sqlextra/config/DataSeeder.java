package com.sql.sqlextra.config;

import com.sql.sqlextra.entity.*;
import com.sql.sqlextra.repository.*;
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

    private final ProductRepository productRepository;
    private final SessionRepository sessionRepository;
    private final SessionParamsRepository sessionParamsRepository;
    private final AccountRepository accountRepository;
    private final AccountSessionRepository accountSessionRepository;
    private final OrderRepository orderRepository;
    private final AbTestRepository abTestRepository;
    private final EventParamsRepository eventParamsRepository;
    private final EmailSentRepository emailSentRepository;
    private final EmailOpenRepository emailOpenRepository;
    private final EmailVisitRepository emailVisitRepository;
    private final PaidSearchCostRepository paidSearchCostRepository;
    private final RevenuePredictRepository revenuePredictRepository;

    private final Faker faker = new Faker(new Locale("en"));
    private final Random random = new Random();

    @Override
    public void run(String... args) {
        if (productRepository.count() > 0) {
            log.info("Database already contains data, skipping seeding");
            return;
        }

        log.info("Starting data seeding...");

        List<Product> products = seedProducts();
        List<Session> sessions = seedSessions();
        List<SessionParams> sessionParams = seedSessionParams(sessions);
        List<Account> accounts = seedAccounts();
        seedAccountSessions(accounts, sessions);
        seedOrders(sessions, products);
        seedAbTests(sessions);
        seedEventParams(sessions);
        seedEmails(accounts, sessions);
        seedPaidSearchCost();
        seedRevenuePredict();

        log.info("Data seeding completed!");
    }

    private List<Product> seedProducts() {
        List<Product> products = new ArrayList<>();
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
            Product product = new Product(
                    (long) i,
                    faker.commerce().productName(),
                    categories[random.nextInt(categories.length)],
                    new BigDecimal(faker.commerce().price().replace(",", "")),
                    faker.lorem().sentence(10) + (random.nextBoolean() ? " 120x60x80 cm" : "")
            );
            products.add(product);
        }

        return productRepository.saveAll(products);
    }

    private List<Session> seedSessions() {
        List<Session> sessions = new ArrayList<>();
        LocalDate startDate = LocalDate.now().minusMonths(3);

        for (int i = 0; i < sessionsCount; i++) {
            Session session = new Session();
            session.setGaSessionId("ga_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16));
            session.setDate(startDate.plusDays(random.nextInt(90)));
            sessions.add(session);
        }

        return sessionRepository.saveAll(sessions);
    }

    private List<SessionParams> seedSessionParams(List<Session> sessions) {
        List<SessionParams> paramsList = new ArrayList<>();
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

        for (Session session : sessions) {
            SessionParams params = new SessionParams();
            params.setGaSessionId(session.getGaSessionId());
            params.setDevice(devices[random.nextInt(devices.length)]);
            params.setBrowser(browsers[random.nextInt(browsers.length)]);
            params.setOperatingSystem(operatingSystems[random.nextInt(operatingSystems.length)]);
            
            // Some sessions will have null or empty language
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

        return sessionParamsRepository.saveAll(paramsList);
    }

    private List<Account> seedAccounts() {
        List<Account> accounts = new ArrayList<>();

        for (int i = 0; i < accountsCount; i++) {
            Account account = new Account();
            account.setSendInterval(7 + random.nextInt(21));
            // About 30% will be unsubscribed, 70% verified
            account.setIsVerified(random.nextInt(100) < 70 ? 1 : 0);
            account.setIsUnsubscribed(random.nextInt(100) < 30 ? 1 : 0);
            accounts.add(account);
        }

        return accountRepository.saveAll(accounts);
    }

    private void seedAccountSessions(List<Account> accounts, List<Session> sessions) {
        List<AccountSession> accountSessions = new ArrayList<>();
        List<Session> usedSessions = new ArrayList<>();

        for (Account account : accounts) {
            int numSessions = random.nextInt(3) + 1;
            for (int i = 0; i < numSessions; i++) {
                Session session = sessions.get(random.nextInt(sessions.size()));
                if (!usedSessions.contains(session)) {
                    usedSessions.add(session);
                    AccountSessionId id = new AccountSessionId(account.getId(), session.getGaSessionId());
                    AccountSession as = new AccountSession();
                    as.setId(id);
                    accountSessions.add(as);
                }
            }
        }

        accountSessionRepository.saveAll(accountSessions);
    }

    private void seedOrders(List<Session> sessions, List<Product> products) {
        List<OrderEntity> orders = new ArrayList<>();

        for (Session session : sessions) {
            int numOrders = random.nextInt(4);
            for (int i = 0; i < numOrders; i++) {
                OrderEntity order = new OrderEntity();
                order.setGaSessionId(session.getGaSessionId());
                order.setItemId(products.get(random.nextInt(products.size())).getItemId());
                orders.add(order);
            }
        }

        orderRepository.saveAll(orders);
    }

    private void seedAbTests(List<Session> sessions) {
        List<AbTest> abTests = new ArrayList<>();

        for (Session session : sessions) {
            if (random.nextInt(10) < 3) {
                AbTest abTest = new AbTest();
                abTest.setGaSessionId(session.getGaSessionId());
                abTest.setTest(random.nextInt(5) + 1);
                abTest.setTestGroup(random.nextInt(2) + 1);
                abTests.add(abTest);
            }
        }

        abTestRepository.saveAll(abTests);
    }

    private void seedEventParams(List<Session> sessions) {
        List<EventParams> events = new ArrayList<>();
        String[] eventNames = {"page_view", "scroll", "add_to_cart", "remove_from_cart", "checkout", "purchase", "sign_up", "login", "user_engagement", "click"};

        for (Session session : sessions) {
            int numEvents = random.nextInt(10) + 1;
            for (int i = 0; i < numEvents; i++) {
                EventParams event = new EventParams();
                event.setGaSessionId(session.getGaSessionId());
                event.setEventDate(session.getDate());
                event.setEventTimestamp(LocalDateTime.now().minusDays(random.nextInt(30)));
                event.setEventName(eventNames[random.nextInt(eventNames.length)]);
                event.setEventParams("{\"key\": \"" + faker.lorem().word() + "\"}");
                events.add(event);
            }
        }

        eventParamsRepository.saveAll(events);
    }

    private void seedEmails(List<Account> accounts, List<Session> sessions) {
        List<EmailSent> emailSents = new ArrayList<>();
        List<EmailOpen> emailOpens = new ArrayList<>();
        List<EmailVisit> emailVisits = new ArrayList<>();

        for (Account account : accounts) {
            int numEmails = random.nextInt(5) + 1;
            for (int i = 0; i < numEmails; i++) {
                EmailSent sent = new EmailSent();
                sent.setIdAccount(account.getId());
                sent.setSentDate(random.nextInt(30));
                sent.setLetterType(random.nextInt(5) + 1);
                sent.setIdMessage("msg_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
                emailSents.add(sent);

                if (random.nextBoolean()) {
                    EmailOpen open = new EmailOpen();
                    open.setIdAccount(account.getId());
                    open.setOpenDate(sent.getSentDate() + random.nextInt(5));
                    open.setLetterType(sent.getLetterType());
                    open.setIdMessage(sent.getIdMessage());
                    emailOpens.add(open);

                    if (random.nextBoolean()) {
                        EmailVisit visit = new EmailVisit();
                        visit.setIdAccount(account.getId());
                        visit.setVisitDate(open.getOpenDate() + random.nextInt(3));
                        visit.setLetterType(sent.getLetterType());
                        visit.setIdMessage(sent.getIdMessage());
                        emailVisits.add(visit);
                    }
                }
            }
        }

        emailSentRepository.saveAll(emailSents);
        emailOpenRepository.saveAll(emailOpens);
        emailVisitRepository.saveAll(emailVisits);
    }

    private void seedPaidSearchCost() {
        List<PaidSearchCost> costs = new ArrayList<>();
        LocalDate startDate = LocalDate.now().minusMonths(3);

        for (int i = 0; i < 90; i++) {
            PaidSearchCost cost = new PaidSearchCost();
            cost.setDate(startDate.plusDays(i));
            cost.setCost(new BigDecimal(random.nextInt(500) + 50));
            costs.add(cost);
        }

        paidSearchCostRepository.saveAll(costs);
    }

    private void seedRevenuePredict() {
        List<RevenuePredict> predicts = new ArrayList<>();
        LocalDate startDate = LocalDate.now().minusMonths(3);

        for (int i = 0; i < 90; i++) {
            RevenuePredict predict = new RevenuePredict();
            predict.setDate(startDate.plusDays(i));
            predict.setPredict(new BigDecimal(random.nextInt(1000) + 200));
            predicts.add(predict);
        }

        revenuePredictRepository.saveAll(predicts);
    }
}