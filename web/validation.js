document.addEventListener("DOMContentLoaded", () => {
    const formulario = document.getElementById("formulario");

    const nombre = document.getElementById("cliente");
    const personas = document.getElementById("personas");
    const precio = document.getElementById("precioPersona");

    nombre.addEventListener("input", () => {
        validarNombre();
    });

    personas.addEventListener("input", () => {
        validarPersonas();
    });

    precio.addEventListener("input", () => {
        validarPrecio();
    });

    formulario.addEventListener("submit", (e) => {
        let valido = true;

        if (!validarNombre()) valido = false;
        if (!validarPersonas()) valido = false;
        if (!validarPrecio()) valido = false;

        if (!valido) e.preventDefault();
    });

    function validarNombre() {
        const nombreRegex = /^[A-Za-zÁÉÍÓÚÑáéíóúñ ]{3,}$/;
        if (!nombreRegex.test(nombre.value)) {
            mostrarError(nombre, "Debe tener al menos 3 letras y solo letras o espacios.");
            return false;
        } else {
            limpiarError(nombre);
            return true;
        }
    }

    function validarPersonas() {
        if (parseInt(personas.value) < 1 || isNaN(personas.value)) {
            mostrarError(personas, "Debe ser mínimo 1 persona.");
            return false;
        } else {
            limpiarError(personas);
            return true;
        }
    }

    function validarPrecio() {
        if (parseInt(precio.value) < 1000 || isNaN(precio.value)) {
            mostrarError(precio, "El precio debe ser mínimo $1000.");
            return false;
        } else {
            limpiarError(precio);
            return true;
        }
    }

    function mostrarError(input, mensaje) {
        const contenedor = input.parentElement;
        const small = contenedor.querySelector("small");
        small.textContent = mensaje;
        contenedor.classList.add("invalid");
    }

    function limpiarError(input) {
        const contenedor = input.parentElement;
        const small = contenedor.querySelector("small");
        small.textContent = "";
        contenedor.classList.remove("invalid");
    }
});
