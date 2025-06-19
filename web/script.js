// Precios por persona según cantidad de personas
function obtenerPrecioPorPersona(personas) {
    if (personas === 1) return 1200;
    if (personas === 2) return 1100;
    if (personas === 3) return 1050;
    if (personas === 4) return 1000;
    if (personas >= 5 && personas <= 9) return 950;
    if (personas >= 10) return 900;
    return 1200;
}

// Generar detalles ficticios de vuelo
function generarDetallesVuelo(personas, destino, fecha) {
    const aviones = [
        { modelo: 'Boeing 787 Dreamliner', asientos: 250 },
        { modelo: 'Airbus A350', asientos: 300 },
        { modelo: 'Boeing 777', asientos: 350 },
        { modelo: 'Airbus A330', asientos: 270 }
    ];
    const horas = ['06:30', '09:45', '13:00', '16:15', '19:30', '22:00'];
    const avion = aviones[Math.floor(Math.random() * aviones.length)];
    const hora = horas[Math.floor(Math.random() * horas.length)];
    let asientos = [];
    for (let i = 0; i < personas; i++) {
        asientos.push(String.fromCharCode(65 + (i % 6)) + (10 + i));
    }
    return {
        avion: avion.modelo,
        asientos: asientos.join(', '),
        hora,
        fecha
    };
}

function calcular() {
    var nombre = document.getElementById('nombre').value;
    var personas = parseInt(document.getElementById('personas').value) || 0;
    var destino = document.getElementById('destino').value;
    var fecha = document.getElementById('fecha').value;
    var seguro = document.getElementById('seguro').checked;

    if (!nombre || !personas || !destino || !fecha) {
        document.getElementById('resultado').innerHTML = '<b>Por favor completa todos los campos.</b>';
        return;
    }

    var precioPorPersona = obtenerPrecioPorPersona(personas);
    var subtotal = personas * precioPorPersona;
    var descuento = personas > 4 ? subtotal * 0.08 : 0;
    var subtotalConDescuento = subtotal - descuento;
    var impuesto = subtotalConDescuento * 0.12;
    var seguroTotal = seguro ? personas * 25000 : 0;
    var total = subtotalConDescuento + impuesto + seguroTotal;

    var detallesVuelo = generarDetallesVuelo(personas, destino, fecha);

    var resultado = '<h3>Factura de tu reserva</h3>';
    resultado += '<b>Datos del cliente:</b><br>';
    resultado += 'Nombre: ' + nombre + '<br>';
    resultado += 'Personas: ' + personas + '<br>';
    resultado += 'Destino: ' + destino + '<br>';
    resultado += 'Fecha de viaje: ' + fecha + '<br><br>';

    resultado += '<b>Detalles del vuelo:</b><br>';
    resultado += 'Avión: ' + detallesVuelo.avion + '<br>';
    resultado += 'Asientos: ' + detallesVuelo.asientos + '<br>';
    resultado += 'Hora de salida: ' + detallesVuelo.hora + '<br>';
    resultado += 'Fecha de salida: ' + detallesVuelo.fecha + '<br><br>';

    resultado += '<b>Detalle de costos:</b><br>';
    resultado += '<ul>';
    resultado += '<li>Precio por persona: $' + precioPorPersona.toLocaleString() + '</li>';
    resultado += '<li>Subtotal: $' + subtotal.toLocaleString() + '</li>';
    resultado += '<li>Descuento (8% si más de 4 personas): $' + descuento.toLocaleString() + '</li>';
    resultado += '<li>Impuesto (12%): $' + impuesto.toLocaleString() + '</li>';
    resultado += '<li>Seguro adicional: $' + seguroTotal.toLocaleString() + '</li>';
    resultado += '<li><b>Total final: $' + total.toLocaleString() + '</b></li>';
    resultado += '</ul>';

    document.getElementById('resultado').innerHTML = resultado;
}