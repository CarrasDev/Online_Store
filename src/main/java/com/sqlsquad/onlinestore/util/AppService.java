package com.sqlsquad.onlinestore.util;

import com.sqlsquad.onlinestore.controlador.ArticuloController;
import com.sqlsquad.onlinestore.controlador.ClienteController;
import com.sqlsquad.onlinestore.controlador.PedidoController;
import com.sqlsquad.onlinestore.modelo.ArticuloModel;
import com.sqlsquad.onlinestore.modelo.ClienteModel;
import com.sqlsquad.onlinestore.modelo.PedidoModel;

public class AppService {

    private static AppService instance;

    private ArticuloModel articuloModel;
    private ClienteModel clienteModel;
    private PedidoModel pedidoModel;

    private ArticuloController articuloController;
    private ClienteController clienteController;
    private PedidoController pedidoController;

    private AppService() {
        // Inicialización de modelos y controladores
        articuloModel = new ArticuloModel();
        clienteModel = new ClienteModel();
        pedidoModel = new PedidoModel();

        articuloController = new ArticuloController(articuloModel);
        clienteController = new ClienteController(clienteModel);
        pedidoController = new PedidoController(pedidoModel, articuloModel, clienteModel);

    }

    public static AppService getInstance() {
        if (instance == null) {
            instance = new AppService();
        }
        return instance;
    }

    public ArticuloController getArticuloController() {
        return articuloController;
    }

    public ClienteController getClienteController() {
        return clienteController;
    }

    public PedidoController getPedidoController() {
        return pedidoController;
    }
}
