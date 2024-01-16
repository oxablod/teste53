package com.teste.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.teste.model.PedidosModel;

import java.util.List;
public class PedidosDTOListWrapper {
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<PedidosModel> element;
    public List<PedidosModel> getElements() {
        return element;
    }

    public void setElements(List<PedidosModel> elements) {
        this.element = element;
    }
}
