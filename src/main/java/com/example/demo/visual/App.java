package com.example.demo.visual;
import com.example.demo.dominio.Estudiante;
import com.example.demo.dominio.EstudianteRepository;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;



import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Created by Dell on 21/07/2017.
 */

@SpringUI
public class App extends UI {





    @Autowired
    EstudianteRepository estudianteRepository;


    @Override
    protected void init (VaadinRequest vaadinRequest){
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout hlayout = new HorizontalLayout();
        TextField nombre = new TextField("Nombree");
        TextField apellido = new TextField("Apellido");
        DateField nacimiento = new DateField("Fecha de Nacimiento");

        Grid<Estudiante> grid = new Grid<>();
        grid.addColumn(Estudiante::getId).setCaption("ID");
        grid.addColumn(Estudiante::getNombre).setCaption("Noombre");
        grid.addColumn(Estudiante::getApellido).setCaption("Apellido");
        grid.addColumn(Estudiante::getEdad).setCaption("Nacimiento");
        grid.addColumn(Estudiante::getGrado).setCaption("Grado");


        Button add = new Button("Adicionar");

        List<String> data = IntStream.range(1, 7).mapToObj(i ->  i+" Primaria " ).collect(Collectors.toList());
        NativeSelect<String> sample = new NativeSelect<>("Seleccione una opcion", data);

        sample.setEmptySelectionAllowed(false);
        //sample.setSelectedItem(data.get(2));
        add.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Estudiante e = new Estudiante();
                e.setNombre(nombre.getValue());
                e.setApellido(apellido.getValue());
                e.setEdad(nacimiento.getValue());
                e.setGrado(sample.getValue());

                estudianteRepository.save(e);
                grid.setItems(estudianteRepository.findAll());

                nombre.clear();
                sample.clear();
                apellido.clear();
                nacimiento.clear();

            }
        });







        hlayout.addComponents(nombre,apellido,nacimiento,sample,add);
        hlayout.setComponentAlignment(add, Alignment.BOTTOM_RIGHT);


        layout.addComponents(hlayout);
        layout.addComponents(grid);

        setContent(layout);
    }
}
