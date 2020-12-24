package io.github.muratcanabay.uniyaz;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import io.github.muratcanabay.uniyaz.db.DbOperations;
import io.github.muratcanabay.uniyaz.domain.Kisi;

import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.List;

@Theme("mytheme")
@Widgetset("com.uniyaz.MyAppWidgetset")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        FormLayout formLayout = new FormLayout();

        TextField nameField = new TextField();
        nameField.setCaption("Adi : ");
        formLayout.addComponent(nameField);

        TextField surnameField = new TextField();
        surnameField.setCaption("Soyadi : ");
        formLayout.addComponent(surnameField);

        TextField phoneField = new TextField();
        phoneField.setCaption("Telefon : ");
        formLayout.addComponent(phoneField);

        Grid grid = new Grid();
        grid.addColumn("Id", Integer.class);
        grid.addColumn("Ad", String.class);
        grid.addColumn("Soyad", String.class);
        grid.addColumn("Tel No.", String.class);

        Button kisiEkleButton = new Button();
        kisiEkleButton.setCaption("Kisi Ekle");
        kisiEkleButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                Kisi kisi = new Kisi();
                kisi.setAd(nameField.getValue());
                kisi.setSoyad(surnameField.getValue());
                kisi.setTelNo(phoneField.getValue());

                DbOperations dbOperations = new DbOperations();
                dbOperations.kisiEkle(kisi);
                Notification.show("Kisi Eklendi.");
            }
        });

        Button kisiListeleButton = new Button();
        kisiListeleButton.setCaption("Kisi Listele");
        kisiListeleButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {

                List<Kisi> kisiList = new ArrayList<>();
                DbOperations dbOperations = new DbOperations();
                kisiList = dbOperations.kisiListele();

                grid.getContainerDataSource().removeAllItems();

                for (Kisi k : kisiList) {
                    grid.addRow(k.getId(), k.getAd(), k.getSoyad(), k.getTelNo());
                }
                formLayout.addComponent(grid);
            }
        });

        Button kisiSilButton = new Button();
        kisiSilButton.setCaption("Kisi Sil");
        kisiSilButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                Kisi kisi = new Kisi();
                kisi.setAd(nameField.getValue());
                kisi.setSoyad(surnameField.getValue());

                DbOperations dbOperations = new DbOperations();
                dbOperations.kisiSil(kisi);
                Notification.show("Kisi basariyla silindi.");
            }
        });

        Button kisiAra = new Button();
        kisiAra.setCaption("Kisi Ara");
        kisiAra.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                List<Kisi> kisiList = new ArrayList<>();
                DbOperations dbOperations = new DbOperations();
                Kisi kisi = new Kisi();
                kisi.setAd(nameField.getValue());
                kisiList = dbOperations.kisiAra(kisi);

                grid.getContainerDataSource().removeAllItems();

                for (Kisi k : kisiList) {
                    grid.addRow(k.getId(), k.getAd(), k.getSoyad(), k.getTelNo());
                }
                formLayout.addComponent(grid);
            }
        });

        formLayout.addComponent(kisiEkleButton);
        formLayout.addComponent(kisiListeleButton);
        formLayout.addComponent(kisiSilButton);
        formLayout.addComponent(kisiAra);

        setContent(formLayout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
