package servico;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class BackupScheduler {

	public static void scheduleBackupJob(SchedulerFactory factory) {
        try {
            JobDetail job = JobBuilder.newJob(BackupJob.class)
                    .withIdentity("backupJob", "group1")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("backupTrigger", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 2 * * ?"))
                    .build();

            Scheduler scheduler = factory.getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);

            System.out.println("Agendamento do backup configurado para as 02:00.");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
	
	public static void main(String[] args) {
        scheduleBackupJob(new StdSchedulerFactory());
    }

}
