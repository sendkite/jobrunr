# Jobrunr

- https://github.com/jobrunr/jobrunr

```shell
+------------------------------+
| Tables_in_mydatabase         |
+------------------------------+
| jobrunr_backgroundjobservers |
| jobrunr_jobs                 |
| jobrunr_jobs_stats           |
| jobrunr_metadata             |
| jobrunr_migrations           |
| jobrunr_recurring_jobs       |
+------------------------------+
```

## 스케줄러 사용법 - @Recurring

- 특정 job을 주기적으로 실행하기 위해서 @Recurring를 사용한다.

```java
// 자동
@Recurring(id = "daily-job", cron = "0 0 * * *", zoneId = "Asia/Seoul")
public void dailyJob() {
    System.out.println("daily job");
}

// 수동
jobScheduler.scheduleRecurrently("every-minute-job", Cron.everyMinute(),
    () -> System.out.println("🚀 1분마다 실행되는 Job!"));

```

- 동작 예시 

![scheduleRecurrently](./docs/stock-distribute.png)