package utilidades;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GestorTemporizadores {

    private ScheduledExecutorService scheduler;

    public void iniciar(Runnable tarea, int intervaloMilis) {
        // Detener cualquier ejecución previa antes de iniciar una nueva
        detener();

        // Creamos un pool de un solo hilo
        scheduler = Executors.newSingleThreadScheduledExecutor();

        // Ejecuta la tarea con un retraso inicial de 0 y un periodo de 'intervaloMilis'
        scheduler.scheduleAtFixedRate(tarea, 0, intervaloMilis, TimeUnit.MILLISECONDS);
    }

    public void detener() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow(); 
// Interrumpe la tarea inmediatamente
        }
    }
}
