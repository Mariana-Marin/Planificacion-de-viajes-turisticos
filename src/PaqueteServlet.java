import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class PaqueteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

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

        out.println("<html><body>");
        out.println("<h2>🧾 Resumen del Paquete Turístico</h2>");
        out.println("<p>Cliente: " + cliente + "</p>");
        out.println("<p>Destino: " + destino + "</p>");
        out.println("<p>Personas: " + personas + "</p>");
        out.println("<p>Subtotal: $" + subtotal + "</p>");
        out.println("<p>Descuento: $" + descuento + "</p>");
        out.println("<p>Impuesto: $" + impuesto + "</p>");
        out.println("<p>Seguro: $" + seguro + "</p>");
        out.println("<h3>Total a pagar: $" + total + "</h3>");
        out.println("</body></html>");
    }
}