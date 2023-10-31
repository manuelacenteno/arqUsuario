package com.example.demo.Usuario.dto;

public class ReporteMonopatinesPorViajeDTO {
    
    private Long monopatin;
    private int anio;
    private Long cantViajes;

    public ReporteMonopatinesPorViajeDTO() {
    }

    public Long getMonopatin() {
        return monopatin;
    }

    public void setMonopatin(Long monopatin) {
        this.monopatin = monopatin;
    }

    public Long getCantViajes() {
        return cantViajes;
    }

    public void setCantViajes(Long cantViajes) {
        this.cantViajes = cantViajes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    @Override
    public String toString() {
        return "ReporteMonopatinesPorViajeDTO [monopatin=" + monopatin + ", anio=" + anio + ", cantViajes=" + cantViajes
                + "]";
    }
}
