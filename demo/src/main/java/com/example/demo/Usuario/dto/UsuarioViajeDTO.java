package com.example.demo.Usuario.dto;


//DEVUELVE SOLO DATOS NO FUNCIONALIDAD LOS DTOS
public class UsuarioViajeDTO {
    private Long idUsuario;
    private Long idUsuarioServ;//idMonopatin


   
    public UsuarioViajeDTO(Long idUsuario, Long idUsuarioServ){
        this.idUsuario = idUsuario;
        this.idUsuarioServ = idUsuarioServ;
    
    }

     public Long getIdUsuario() {
        return idUsuario;
    }

    public Long getIdUsuarioServ() {
        return idUsuarioServ;
    }


    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }


    public void setIdUsuarioServ(Long idUsuarioServ) {
        this.idUsuarioServ = idUsuarioServ;
    }


    @Override
    public String toString() {
        return "UsuarioViajeDTO [idUsuario=" + idUsuario + ", idUsuarioServ=" + idUsuarioServ + "]";
    }

    
}