import java.io.*;
import java.text.NumberFormat;
import java.util.Locale;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class PaqueteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        NumberFormat formatoPesos = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

        String cliente = request.getParameter("cliente");
        int personas = Integer.parseInt(request.getParameter("personas"));
        String destino = request.getParameter("destino");
        double precioPorPersona = Double.parseDouble(request.getParameter("precioPersona"));
        boolean deseaSeguro = request.getParameter("seguro") != null;

        double subtotal = personas * precioPorPersona;
        double descuento = (personas > 4) ? subtotal * 0.08 : 0;
        subtotal -= descuento;
        double impuesto = subtotal * 0.12;
        double seguro = deseaSeguro ? personas * 25000 : 0;
        double total = subtotal + impuesto + seguro;

        out.println("<!DOCTYPE html>");
        out.println("<html lang='es'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Resumen del Paquete</title>");
        out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.9.3/html2pdf.bundle.min.js'></script>");
        out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/qrious/4.0.2/qrious.min.js'></script>");
        out.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css'>");

        out.println("<style>");
        out.println("@keyframes fadeIn { from { opacity: 0; transform: translateY(20px); } to { opacity: 1; transform: translateY(0); } }");
        out.println("body { font-family: 'Segoe UI', sans-serif; background: linear-gradient(135deg, #a8edea, #fed6e3); padding: 2em; margin: 0; }");
        out.println(".container { background: #ffffff; padding: 2.5em 3em; border-radius: 20px; max-width: 700px; margin: auto; box-shadow: 0 10px 30px rgba(0,0,0,0.15); animation: fadeIn 0.8s ease; transition: all 0.3s ease; }");
        out.println("h2 { text-align: center; color: #2c3e50; margin-bottom: 1em; }");
        out.println("p { font-size: 1.1em; color: #333; margin: 0.4em 0; }");
        out.println("strong { color: #2d3436; }");
        out.println("h3 { text-align: center; color: #16a085; margin-top: 2em; }");
        out.println(".highlight { font-size: 1.4em; font-weight: bold; color: #b22222; }");
        out.println(".button-container { text-align: center; margin-top: 2em; display: flex; justify-content: center; gap: 1em; flex-wrap: wrap; }");
        out.println("button, .btn { background: #16a085; color: white; padding: 0.8em 1.5em; border: none; border-radius: 10px; font-size: 1em; cursor: pointer; text-decoration: none; transition: background 0.3s ease; }");
        out.println("button:hover, .btn:hover { background: #138d75; }");
        out.println("@media (max-width: 600px) { .container { padding: 1.5em; } }");
        out.println("</style>");

        out.println("<script>");
        out.println("function descargarPDF() {");
        out.println("  const resumen = document.getElementById('resumen');");
        out.println("  const now = new Date();");
        out.println("  const fecha = now.toLocaleDateString('es-CO', { day: '2-digit', month: 'long', year: 'numeric' });");
        out.println("  const numeroOrden = Math.floor(100000 + Math.random() * 900000);");
        out.println("  const total = resumen.querySelector('.highlight')?.innerText || '';");
        out.println("  const cliente = resumen.querySelector('p')?.innerText || '';");
        out.println("  const qrData = `${cliente}\\n${total}\\nOrden: #${numeroOrden}`;");
        out.println("  const qr = new QRious({ value: qrData, size: 100 });");

        out.println("  const extraHTML = `");
        out.println("    <div style='margin-bottom: 2em;'>");
        out.println("      <p><strong>📅 Fecha de emisión:</strong> ${fecha}</p>");
        out.println("      <p><strong>🧾 Número de orden:</strong> #${numeroOrden}</p>");
        out.println("      <img src='${qr.toDataURL()}' alt='Código QR' style='margin-top: 1em;'>");
        out.println("    </div>`;");

        out.println("  const contenidoHTML = `");
        out.println("    <div style='font-family: Segoe UI, sans-serif; padding: 2em; max-width: 700px; background: #f0f0f0; color: #000;'>");
        out.println("      ${extraHTML}${resumen.innerHTML}");
        out.println("    </div>`;");

        out.println("  const tempDiv = document.createElement('div');");
        out.println("  tempDiv.innerHTML = contenidoHTML;");
        out.println("  document.body.appendChild(tempDiv);");

        out.println("  html2pdf().from(tempDiv).set({");
        out.println("    margin: 10,");
        out.println("    filename: 'Resumen-Paquete.pdf',");
        out.println("    html2canvas: { scale: 2 },");
        out.println("    jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' }");
        out.println("  }).save().then(() => {");
        out.println("    document.body.removeChild(tempDiv);");
        out.println("  });");
        out.println("}");
        out.println("</script>");

        out.println("</head>");
        out.println("<body>");

        out.println("<div class='container' id='resumen'>");
        out.println("<h2><i class='fas fa-suitcase-rolling'></i> Resumen del Paquete Turístico</h2>");
        out.println("<p><strong>👤 Cliente:</strong> " + cliente + "</p>");
        out.println("<p><strong>🗺️ Destino:</strong> " + destino + "</p>");
        out.println("<p><strong>👥 Personas:</strong> " + personas + "</p>");
        out.println("<p><strong>💵 Subtotal:</strong> " + formatoPesos.format(subtotal) + "</p>");
        out.println("<p><strong>🎁 Descuento:</strong> " + formatoPesos.format(descuento) + "</p>");
        out.println("<p><strong>🧾 Impuesto:</strong> " + formatoPesos.format(impuesto) + "</p>");
        out.println("<p><strong>🛡️ Seguro:</strong> " + formatoPesos.format(seguro) + "</p>");
        out.println("<h3><span class='highlight'>💰 Total a pagar: " + formatoPesos.format(total) + "</span></h3>");
        out.println("</div>");

        out.println("<div class='button-container'>");
        out.println("<a href='index.html' class='btn'><i class='fas fa-arrow-left'></i> Volver</a>");
        out.println("<button onclick='descargarPDF()'><i class='fas fa-file-pdf'></i> Descargar PDF</button>");
        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }
}
