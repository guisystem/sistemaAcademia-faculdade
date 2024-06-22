package servico;

import java.io.File;
import java.io.IOException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class BackupJob implements Job{
	
	@Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            String database = "sistemaacademiadb";
            String caminhoBackup = "F:\\TCC - Sistema de Gerenciamento de Academia\\Backup";
            String backupPath = caminhoBackup + "\\sistemaacademiaBackup.sql";
            String comando = String.format("C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump --defaults-extra-file=C:\\Users\\Windows\\sistemaacademia-Backup.my.cnf %s -r \"%s\"", database, backupPath);

            if (!checarECriarDiretorio(caminhoBackup)) {
                return;
            }

            Process processo = executarComando(comando);

            int resultado = processo.waitFor();
            resultadoBackup(resultado);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected boolean checarECriarDiretorio(String caminhoBackup) {
        File diretorioBackup = new File(caminhoBackup);
        if (!diretorioBackup.exists()) {
            System.out.println("Criando diretório de backup: " + caminhoBackup);
            return diretorioBackup.mkdirs();
        } else {
            System.out.println("Diretório de backup já existe: " + caminhoBackup);
            return true;
        }
    }

    protected void resultadoBackup(int exitCode) {
        if (exitCode == 0) {
            System.out.println("Backup realizado com sucesso.");
        } else {
            System.out.println("Falha na execução do backup. Código de saída: " + exitCode);
        }
    }

    @SuppressWarnings("deprecation")
	protected Process executarComando(String comando) throws IOException {
        return Runtime.getRuntime().exec(comando);
    }
	
}
