# Checkpoint 1 — Beans, Dependency Injection & Startup

> Date: 2026-10-29  
> Project: spring-boot learning repo

---

## 1. What Is a Bean?

A **bean** is any object that Spring creates, manages, and wires up for you. Instead of writing `new PaymentService()` everywhere, you let Spring hold one instance and hand it out wherever it's needed.

### Two ways to register a bean

**Way 1 — Stereotype annotations (on the class itself)**

Put one of these on your class and Spring finds it automatically via component scanning:

- `@Component` — generic bean
- `@Service` — same thing; signals business logic
- `@Repository` — same thing; signals data layer
- `@Controller` / `@RestController` — same thing; signals HTTP handler

```java
@Component
public class PaymentService {
    public void pay() { System.out.println("Paid!"); }
}
```

Spring sees the annotation, creates one instance, done.

**Way 2 — `@Configuration` + `@Bean` methods**

Create a class annotated `@Configuration` (any name works) and write methods annotated `@Bean`. Each method returns an object Spring registers.

```java
@Configuration
public class AppConfig {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService();
    }
}
```

**When to use which:**

| Situation | Use |
|---|---|
| You own the class | `@Component` / `@Service` (simpler) |
| Library class you can't annotate | `@Configuration` + `@Bean` (only way) |
| Need custom construction logic | `@Configuration` + `@Bean` |

---

## 2. Dependency Injection — `@Autowired`

Dependency injection means: **Spring gives a bean the other beans it needs**, instead of the bean creating them itself.

```java
@RestController
public class HelloController {

    @Autowired
    private PaymentService paymentService;  // Spring fills this in

    // OR via constructor (preferred):
    public HelloController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

Both approaches do the same thing — Spring looks up the `PaymentService` bean and passes it in. Constructor injection is preferred because the dependency is explicit and the field can be `final`.

---

## 3. The Static Trap — Why `@Autowired` Fails on Static Fields

This **does not work**:

```java
@Autowired
static PaymentService paymentService = null;  // ❌ Spring ignores this

public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
    paymentService.pay();  // 💥 NullPointerException — still null
}
```

**Why:**

- `static` fields belong to the **class**, not to any instance
- `@Autowired` injects into **instance** fields of beans Spring manages
- Spring literally skips static fields — the injection never happens

**The catch-22:**

| | `@Autowired` injects? | `main()` can access? |
|---|---|---|
| `static` field | ❌ No | ✅ Yes |
| Instance field | ✅ Yes | ❌ No (main is static) |

You need a **non-static method** where you can use injected beans → that's where `CommandLineRunner` comes in.

---

## 4. CommandLineRunner — Run Code at Startup

`CommandLineRunner` is an interface with one method — `run(String... args)` — that Spring calls **once, right after the app boots**.

It is **not** an injection mechanism. It's simply a **non-static method** called by Spring, where injected beans are available.

```java
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    PaymentService paymentService;  // ✅ non-static, Spring injects it

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        paymentService.pay();  // ✅ works — bean is injected, method is non-static
    }
}
```

**Common uses:**
- Seed a database with initial data
- Print diagnostic info at startup
- Verify an external service is reachable

---

## 5. Spring Boot Startup Sequence

Everything happens inside `SpringApplication.run()`:

```
main()
└── SpringApplication.run(Application.class, args)
    ├── 1. Create the ApplicationContext (the container)
    ├── 2. Scan for components (@Component, @Service, @Configuration, etc.)
    ├── 3. Create all beans
    ├── 4. Wire them together (@Autowired injection)
    ├── 5. Start the embedded Tomcat server (port 8080)
    ├── 6. Call CommandLineRunner.run()     ← near the end, everything is ready
    └── return back to main()
```

**Key insight:** `CommandLineRunner.run()` happens **inside** `SpringApplication.run()`, not after it. By the time it's called, every bean exists and is fully injected.

Print order with our code:
```
Creating Bean from appConfig!!     ← step 3 (bean creation)
Hello from Payment service!!!      ← step 6 (CommandLineRunner.run())
Hello there!!!                     ← back in main(), after Spring returns
```

---

## 6. Quick Reference

| Concept | What it does | How |
|---|---|---|
| **Bean** | An object Spring manages | `@Component`, `@Service`, `@Bean`, etc. |
| **`@Configuration`** | Marks a class as "contains `@Bean` definitions" | Any class name works |
| **`@Autowired`** | Tells Spring to inject a dependency | On instance fields or constructors (NOT static) |
| **Constructor injection** | Preferred way to inject | Just declare constructor params; Spring fills them |
| **`CommandLineRunner`** | Runs code once after boot | Implement interface, put logic in `run()` |
| **`SpringApplication.run()`** | Boots the entire framework | Creates beans → injects → starts server → calls runners |
| **`static` + `@Autowired`** | **Broken combo** | Spring ignores static fields — always null |

---

*Next checkpoint: controllers, request mapping, REST endpoints*
