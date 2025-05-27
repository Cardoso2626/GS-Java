package br.com.fiap.gsjava.model;

import jakarta.persistence.*;

@Table(name = "tb_localizacao")
@Entity
public class Localizacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private double latitude;
    private double longitude;
    private Usuario idUsuario;


    public Localizacao(double latitude, double longitude, Usuario idUsuario) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.idUsuario = idUsuario;
    }
    public Localizacao() {}

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }
}
