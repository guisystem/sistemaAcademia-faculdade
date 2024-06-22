package servico;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.quartz.JobExecutionContext;

class BackupJobTeste {

	private BackupJob backupJob;
    private JobExecutionContext jobExecutionContext;

    @BeforeEach
    public void setUp() {
        backupJob = Mockito.spy(new BackupJob());
        jobExecutionContext = mock(JobExecutionContext.class);
    }

    @Test
    public void testarExecuteBackupSucesso() throws Exception {
        doReturn(true).when(backupJob).checarECriarDiretorio(anyString());
        doNothing().when(backupJob).resultadoBackup(0);

        Process processo = mock(Process.class);
        when(processo.waitFor()).thenReturn(0);
        doReturn(processo).when(backupJob).executarComando(anyString());

        backupJob.execute(jobExecutionContext);

        verify(backupJob, times(1)).checarECriarDiretorio(anyString());
        verify(backupJob, times(1)).resultadoBackup(0);
    }

    @Test
    public void testarExecuteBackupFalhou() throws Exception {
        doReturn(true).when(backupJob).checarECriarDiretorio(anyString());
        doNothing().when(backupJob).resultadoBackup(1);

        Process processo = mock(Process.class);
        when(processo.waitFor()).thenReturn(1);
        doReturn(processo).when(backupJob).executarComando(anyString());

        backupJob.execute(jobExecutionContext);

        verify(backupJob, times(1)).checarECriarDiretorio(anyString());
        verify(backupJob, times(1)).resultadoBackup(1);
    }

    @Test
    public void testarCheckAndCreateBackupDir() {
        doCallRealMethod().when(backupJob).checarECriarDiretorio(anyString());

        boolean result = backupJob.checarECriarDiretorio("non_existing_directory");

        assertTrue(result);
    }
    
    @Test
    void handleExitCode() {
    	backupJob.resultadoBackup(0);
    	backupJob.resultadoBackup(1);
    }

}
