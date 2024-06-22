package servico;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

class BackupSchedulerTeste {

	private Scheduler scheduler;
	@SuppressWarnings("unused")
	private JobDetail jobDetail;
	@SuppressWarnings("unused")
	private Trigger trigger;

    @BeforeEach
    public void iniciarTeste() throws SchedulerException {
    	scheduler = Mockito.mock(Scheduler.class);
    	jobDetail = Mockito.mock(JobDetail.class);
    	trigger = Mockito.mock(Trigger.class);

        when(scheduler.scheduleJob(any(JobDetail.class), any(Trigger.class))).thenReturn(new Date());
    }

    @Test
    public void testarScheduleBackupJob() throws SchedulerException {
        StdSchedulerFactory factoryMock = Mockito.mock(StdSchedulerFactory.class);
        when(factoryMock.getScheduler()).thenReturn(scheduler);

        BackupScheduler.scheduleBackupJob(factoryMock);

        verify(scheduler, times(1)).start();
        verify(scheduler, times(1)).scheduleJob(any(JobDetail.class), any(Trigger.class));
    }

}
