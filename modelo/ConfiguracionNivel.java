package modelo;

public class ConfiguracionNivel {

    private int idNivel;
    private int nivel;
    private int tiempoBaseSegundos;
    private Integer pedidosParaSubir;
    private Integer puntosParaSubir;

    public ConfiguracionNivel() {
    }

    public ConfiguracionNivel(int idNivel, int nivel, int tiempoBaseSegundos,
            Integer pedidosParaSubir, Integer puntosParaSubir) {
        this.idNivel = idNivel;
        this.nivel = nivel;
        this.tiempoBaseSegundos = tiempoBaseSegundos;
        this.pedidosParaSubir = pedidosParaSubir;
        this.puntosParaSubir = puntosParaSubir;
    }

    public int getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(int idNivel) {
        this.idNivel = idNivel;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getTiempoBaseSegundos() {
        return tiempoBaseSegundos;
    }

    public void setTiempoBaseSegundos(int tiempoBaseSegundos) {
        this.tiempoBaseSegundos = tiempoBaseSegundos;
    }

    public Integer getPedidosParaSubir() {
        return pedidosParaSubir;
    }

    public void setPedidosParaSubir(Integer pedidosParaSubir) {
        this.pedidosParaSubir = pedidosParaSubir;
    }

    public Integer getPuntosParaSubir() {
        return puntosParaSubir;
    }

    public void setPuntosParaSubir(Integer puntosParaSubir) {
        this.puntosParaSubir = puntosParaSubir;
    }
}