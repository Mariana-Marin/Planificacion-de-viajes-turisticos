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
        double seguro = deseaSeguro ? personas * 25_000 : 0;
        double total = subtotal + impuesto + seguro;

        out.println("<!DOCTYPE html>");
        out.println("<html lang='es'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Resumen del Paquete</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background: linear-gradient(to right, #e3ffe7, #d9e7ff); padding: 2em; }");
        out.println(".container { background: white; padding: 2em; border-radius: 15px; max-width: 600px; margin: auto; box-shadow: 0 0 20px rgba(0,0,0,0.1); }");
        out.println("h2, h3 { color: #003366; }");
        out.println("p { font-size: 1.1em; margin: 0.5em 0; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h2>🧾 Resumen del Paquete Turístico</h2>");
        out.println("<p><strong>Cliente:</strong> " + cliente + "</p>");
        out.println("<p><strong>Destino:</strong> " + destino + "</p>");
        out.println("<p><strong>Personas:</strong> " + personas + "</p>");
        out.println("<p><strong>Subtotal:</strong> " + formatoPesos.format(subtotal) + "</p>");
        out.println("<p><strong>Descuento:</strong> " + formatoPesos.format(descuento) + "</p>");
        out.println("<p><strong>Impuesto:</strong> " + formatoPesos.format(impuesto) + "</p>");
        out.println("<p><strong>Seguro:</strong> " + formatoPesos.format(seguro) + "</p>");
        out.println("<h3><strong>Total a pagar:</strong> " + formatoPesos.format(total) + "</h3>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
