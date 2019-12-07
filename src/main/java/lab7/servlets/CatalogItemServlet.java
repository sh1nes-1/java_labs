package lab7.servlets;

import lab7.model.Catalog;
import lab7.model.CatalogItem;
import lab7.model.Shop;
import lab7.model.SmartPhone;
import lab7.utils.GlobalConfig;
import lab7.exception.ServiceException;
import lab7.service.CatalogService;
import lab7.service.SmartPhoneService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = "/item")
public class CatalogItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rawCatalogId = req.getParameter("cat_id");
        String rawSmartPhoneId = req.getParameter("sm_id");

        if (rawCatalogId == null || rawSmartPhoneId == null) {
            resp.sendRedirect("./");
            return;
        }

        long catalogId, smartPhoneId;
        try {
            catalogId = Long.parseLong(rawCatalogId);
            smartPhoneId = Long.parseLong(rawSmartPhoneId);
        } catch (Exception ex) {
            resp.sendRedirect("./");
            return;
        }

        // Get Catalog
        CatalogService catalogService;
        Optional<Catalog> optionalCatalog;
        try {
            catalogService = new CatalogService();
            optionalCatalog = catalogService.findByIdEager(catalogId);
        } catch (ServiceException e) {
            resp.sendRedirect("./");
            return;
        }

        if (optionalCatalog.isEmpty()) {
            resp.sendRedirect("./");
            return;
        }
        Catalog catalog = optionalCatalog.get();

        // Get Shop
        Optional<Shop> optionalShop;
        try {
            optionalShop = catalogService.getShop(catalog);
        } catch (ServiceException e) {
            resp.sendRedirect("./");
            return;
        }

        if (optionalShop.isEmpty()) {
            resp.sendRedirect("./");
            return;
        }

        Shop shop = optionalShop.get();

        // Set image root
        ServletContext application = getServletConfig().getServletContext();
        String imagesRoot = (String) application.getAttribute("shop.images.root");

        shop.setImageUrl(imagesRoot + shop.getImageUrl());

        // Get SmartPhone
        Optional<SmartPhone> optionalSmartPhone;
        try {
            SmartPhoneService smartPhoneService = new SmartPhoneService();
            optionalSmartPhone = smartPhoneService.findById(smartPhoneId);
        } catch (ServiceException ex) {
            ex.printStackTrace();
            resp.sendRedirect("./");
            return;
        }

        if (optionalSmartPhone.isEmpty()) {
            resp.sendRedirect("./");
            return;
        }

        // Get CatalogItem
        Optional<CatalogItem> optionalCatalogItem = catalog.getSmartPhoneInfo(optionalSmartPhone.get());
        if (optionalCatalogItem.isEmpty()) {
            resp.sendRedirect("./");
            return;
        }

        // Set Attributes for JSP
        req.setAttribute("catalog", catalog);
        req.setAttribute("shop", shop);
        req.setAttribute("item", optionalCatalogItem.get());

        // Show page
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/catalogitem.jsp");
        requestDispatcher.forward(req, resp);
    }
}
