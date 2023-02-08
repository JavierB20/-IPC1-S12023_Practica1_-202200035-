/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.ipc_practica1;

import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Javier
 */


class Productos {
    String nombre;
    double precio;
    int cantComprada;
    int totalCantComp;
}

class Cupones {
    String codigo;
    int monto;
}

public class IPC_Practica1 {
    
    //Variables globales para pedir datos
    public static Scanner scStr = new Scanner(System.in);
    public static Scanner scNum = new Scanner(System.in);

    
    //arreglos globales para guardar datos
    public static Productos[] productos = new Productos[100];
    public static Cupones[] cupones = new Cupones[100];

    
    //Variables para guardar montos totales
    public static double totalCompra;
    public static double subTotal;
    public static double montoCupon;
    
    
    //Variables globales para pedir datos del usuario
    public static String nombre="";
    public static String nit="";
    public static String cupon="";
    
    public static void main(String[] args) {
        
        int opcion; 
        boolean inicio=true;

        String usuario = "cajero_202200035"; 
        String contraseña = "ipc1_202200035";
        System.out.println("Ingreso de sesión de super-25");
        while (inicio) {
            System.out.println("Ingrese su usuario: ");
            String usuarioIngresar = scStr.nextLine();
            System.out.println("Ingrese su contraseña: ");
            String contraseñaIngresar = scStr.nextLine();
            
            
            //Validacion de contraseña y usuario
            if (usuario.equals(usuarioIngresar) && contraseña.equals(contraseñaIngresar)) {
                //Menu inicial con las distintas opciones
                while(inicio){
                    System.out.println("********* Super 25 *********");
                    System.out.println("* 1 -->  Agregar producto  *");
                    System.out.println("* 2 --> Agregar descuentos *");
                    System.out.println("* 3 -->        Vender      *");
                    System.out.println("* 4 -->  Generar Reporte   *");
                    System.out.println("* 5 -->       Salir        *");
                    System.out.println("****************************");

                    opcion = scNum.nextInt();

                    switch (opcion) {
                        case 1:
                            //metodo para ingresar productos
                            RegistrarProductos();
                            break;
                        case 2:
                            //metodo para agregar cupones de descuento
                            RegistrarCupones();
                            break;
                        case 3:
                            //metodo para realizar ventas
                            PedirDatos();
                            MostrarProductos();
                            Vender();
                            TotalCompra();
                            ValidezDeCupon();
                            GenerarFactura();
                            break;
                        case 4:
                            //metodo para generar reporte
                            GenerarReporte();
                            break;
                        default:
                            inicio = false;
                             break;
                    }

                }
            }
            else {
                System.out.println("Usuario y/o contraseña invalidos!!");
            }
        }

    }
    
    
    //Metodos para registrar 
    public static void RegistrarProductos() {
        Productos producto = new Productos();

        System.out.println("**** Menú de ingreso de Productos ****");
        
        System.out.println("Ingrese nombre del producto");
        String nombre = scStr.nextLine();
        producto.nombre = nombre;
        
        //verificar si el producto ingresado ya existe
        for (int i = 0; i < productos.length; i++) {
            if (productos[i] != null) {
                if((productos[i].nombre).equals(producto.nombre)) {
                    System.out.println("El producto que esta ingresando ya existe.");
                    return;
                }
            }
        }
        

        producto.cantComprada = 0;
        producto.totalCantComp = 0;
        
        System.out.println("Ingrese precio del producto");
        double precio = scNum.nextDouble();
        producto.precio = precio;
        
        //Verificar el precio sea mayor a 0
        if (precio < 0) {
            System.out.println("El precio del producto debe ser mayor a cero");
            return;
        }
        
        
        //Ingresa en el arreglo el producto nuevo
        for (int i = 0; i < productos.length; i++) {
            if (productos[i] == null) {
                productos[i] = producto;
                break;
            }
        }
    }
    
    
    public static void RegistrarCupones() {
        Cupones cupon = new Cupones();
        
        System.out.println("**** Menú de ingreso de Cupones ****");
        
        System.out.println("Ingrese código del cupon");
        String codigo = scStr.nextLine();
        cupon.codigo = codigo;

        //verificar que el codigo sea de 4 digitos
        if ((cupon.codigo).length() != 4) {
            System.out.println("El código del cupon debe de ser de 4 digitos");
            return;
        }
        
        
        //verificar si el cupon ingresado ya existe
        if (cupones.length != 0) {
            for (int i = 0; i < cupones.length; i++) {
                if (cupones[i] != null) {
                    if((cupones[i].codigo).equals(cupon.codigo)) {
                        System.out.println("El cupon que esta ingresando ya existe.");
                        return;
                    }
                }
            }
        }
        
        
        System.out.println("Ingrese el descuento del cupon");
        int monto = scNum.nextInt();
        cupon.monto = monto;

        //verificar que el monto del cupon sea mayor a 0 y menor a 100
        if(monto <= 0 && monto >= 100) {
            System.out.println("El porcentaje de descuento debe se mayor a 0 o menor a 100");
            return;
        }
        
        for (int i = 0; i < cupones.length; i++) {
            if (cupones[i] == null) {
                cupones[i] = cupon;
                break;
            }
        }
    }

    //Metodos para mostrar
    public static void MostrarProductos() {
        System.out.println("**** Listado de productos ****");
        for (int i = 0; i < productos.length; i++) {
            if (productos[i] == null) {
                break;
            }
            else {
                System.out.println((i+1) + " -> " + productos[i].nombre + " - " + productos[i].precio);
            }
        }
    }
    
    public static void MostrarCupones() {
        System.out.println("**** Listado de cupones ****");
        for (int i = 0; i < cupones.length; i++) {
            if (cupones[i] == null) {
                break;
            }
            else {
                System.out.println((i+1) + " -> " + cupones[i].codigo + " - " + cupones[i].monto);
            }
        }
    }
    
    //Metodo para vender
    public static void Vender(){
        boolean finalizar=true;
        Productos producto = new Productos();
        producto.totalCantComp = 0;
        System.out.println("**** Sistema de ventas Super 25 ****");

        //Mantiene la venta hasta Finalizarla
        while (finalizar) {
            System.out.println("Ingrese el numero del producto que se vendera o 0 para continuar: ");
            int nomProducto = scNum.nextInt();   
            
            if(nomProducto != 0) {
                //Adquiere la cantidad de producto vendido
                for (int i = 0; i < productos.length; i++) {
                    if (i == (nomProducto - 1) && productos[i] != null) {
                        System.out.println("Ingrese cuantos productos compraras de " + productos[i].nombre);
                        int cantProducto = scNum.nextInt();  
                        
                        if(productos[i].totalCantComp == 0) {
                            productos[i].totalCantComp = cantProducto;
                            productos[i].cantComprada = cantProducto;
                        }
                        else {
                            productos[i].totalCantComp = productos[i].totalCantComp + cantProducto;
                            productos[i].cantComprada = cantProducto;
                        }

                        
                        MostrarProductos();
                        break;
                    }
                }
            }
            else {
                finalizar = false;
                break;
            }

        }
        
        
    }
   
    //Metodo para realizar factura
    public static void GenerarFactura() {
        //Obtener la fecha actual
        DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy, HH:mm:ss");
        String date = dateFormat.format(new Date());
        
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("**************************************** \n");
        System.out.println("************    SUPER-25    ************");
        System.out.println("Cajero: Jose Javier Bonilla Salazar \n");
        System.out.println("Cliente: " + nombre);
        System.out.println("Nit: " + nit);
        System.out.println("Fecha: " + date);
        
        
        System.out.println("**** Listado de productos comprados ****");
        for (int i = 0; i < productos.length; i++) {
            if (productos[i] == null){
                break;
            }
            if (productos[i].cantComprada != 0) {
                System.out.println(productos[i].nombre + " - Q" + productos[i].precio);
                System.out.println("\t\tTotal:" + (productos[i].cantComprada * productos[i].precio));
                System.out.println("\t\tCantidad:" + productos[i].cantComprada);
            }
        }
        
        System.out.println("\tSubtotal: " + subTotal);
        if (!cupon.equals("")) {
            System.out.println("\tDescuento: " + (montoCupon) + "%");
        }
        
        System.out.println("\tTotal: " + totalCompra);
        

        System.out.println("**************************************** \n");
        System.out.println("\n");
        System.out.println("\n");
        ReiniciarProducto();
        
    }

    
    //Metodo para recolectar datos del cliente
    public static void PedirDatos() {
        
        boolean continuar = true;
        
        //Pedir el nombre del usuario y dejar continuar hasta que lo ingrese
        while (continuar) {
            System.out.println("Ingrese el nombre del cliente: ");
            nombre = scStr.nextLine();
            if (!nombre.equals("")) {
                continuar = false;
            }
        }
        
        
        //Pedir nit del cliente/ asignar C/F
        System.out.println("Ingrese el nit del cliente: ");
        nit = scStr.nextLine();
        if (nit.equals("")) {
            nit = "C/F";
        }

    }
    
    //Metodo para mostrar total de compra
    public static void TotalCompra() {
        System.out.println("***************");
        System.out.println("Total de la venta");
        for (int i = 0; i < productos.length; i++) {
            if (productos[i] == null){
                break;
            }
            else if (productos[i].cantComprada != 0) {
                totalCompra = (productos[i].cantComprada * productos[i].precio) + totalCompra;
            }               
        }
        System.out.println("El total de compra: " + totalCompra);

    }
    
    //Metodo para pedir cupon
    public static void ValidezDeCupon() {
        boolean continuar = true;
        subTotal = totalCompra;
                
        while(continuar){
            System.out.println("Ingrese un cupon: ");
            cupon = scStr.nextLine();
            
            if (cupon.equals("")) {
                continuar = false;
            }
            else {
                for (int i = 0; i < cupones.length; i++) {
                    if (cupones[i] == null) {
                        break;
                    }
                    else if (cupon.equals(cupones[i].codigo)) {
                        double monto = cupones[i].monto;
                        double descuento = (monto/100);
                        totalCompra = totalCompra - (totalCompra * descuento);
                        montoCupon = cupones[i].monto;
                        continuar = false;
                    }
                }
                System.out.println("El cupon ingresado es invalido");
            }
        }
    }
    
    //Metodo Reinicar cantidad Comprada 
    public static void ReiniciarProducto() {
        for (int i = 0; i < productos.length; i++) {
            if (productos[i] == null) {
                break;
            }
            else {
                productos[i].cantComprada = 0;
            }
        }
    }
    
    //Metodo para mostrar Reporte
    public static void GenerarReporte(){
        System.out.println("**** Reporte de Ventas ****");


        //ordenamiento burbuja para mostrar el producto mas vendido
        for (int i = 0; i < productos.length; i++) {
            for (int j = 0; j < productos.length - 1; j++) {

                if (productos[j] == null || productos[j+1] == null) {
                    break;
                }
                
                Productos elementoActual = productos[j];
                Productos elementoSiguiente = productos[j+1];
                
                if (elementoActual.totalCantComp < elementoSiguiente.totalCantComp){
                    productos[j] = elementoSiguiente;
                    productos[j+1] = elementoActual;
                }
            }
        } 
        
        for (int i = 0; i < productos.length; i++) {
            if (productos[i] == null) {
                break;
            }
            else {
                System.out.println(productos[i].nombre + " --- " + productos[i].totalCantComp);
            }
        }
        System.out.println("***************************");
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
    }
    
    
}


