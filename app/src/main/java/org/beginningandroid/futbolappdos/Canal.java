package org.beginningandroid.futbolappdos;

public class Canal {
        private String nombre;
        private String site;

        // Constructor vacío necesario para Firebase
        public Canal() { }

        public Canal(String nombre, String site) {
            this.nombre = nombre;
            this.site = site;
        }

        public String getNombre() {
            return nombre;
        }

        public String getSite() {
            return site;
        }
}
