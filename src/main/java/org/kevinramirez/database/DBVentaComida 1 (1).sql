drop database if exists DBrestaurante;
create database DBrestaurante;
use DBrestaurante;

create table Clientes (
	idCliente int auto_increment, 
    nombreCliente varchar(64), 
    apellidoCliente varchar(64),
    nitCliente varchar(20),
    correoCliente varchar(64),
    contrasenaCliente varchar(64),
    telefonoCliente varchar(20),
    constraint pk_cliente primary key (idCliente)
); 

create table Empleados (
	idEmpleado int auto_increment, 
    nombreEmpleado varchar(64), 
    apellidoEmpleado varchar(64), 
    puestoEmpleado varchar(64), 
    telefonoEmpleado varchar(20), 
    correoEmpleado varchar(64),
    sueldoEmpleado double (10,2),
    constraint pk_empleado primary key (idEmpleado)
); 

-- Tabla Comidas
create table Comidas (
    idComida int auto_increment, 
    nombreComida varchar(250), 
    tipo varchar(100),
    precio decimal(6,2),
    constraint pk_comida primary key (idComida)
);

-- Tabla Bebidas
create table Bebidas (
    idBebida int auto_increment,
    nombreBebida varchar(250),
    tipo varchar(100),
    precio decimal(6,2),
    constraint pk_bebida primary key (idBebida)
);

create table Pedidos (
	idPedido int auto_increment, 
    idCliente int, 
    idEmpleado int,  
    fechaPedido datetime,
    metodoPago enum('Efectivo','Tarjeta'), 
    estadoFactura enum('Pagado','Pendiente'), 
    constraint pk_pedido primary key (idPedido),
    constraint fk_pedido_cliente foreign key (idCliente) references Clientes(idCliente),
    constraint fk_pedido_empleado foreign key (idEmpleado) references Empleados(idEmpleado)
); 

create table DetallePedido (
	idDetalle int auto_increment, 
    idPedido int, 
    idComida int,  
    idBebida int,
    cantidad int,
    constraint pk_detalle primary key (idDetalle),
    constraint fk_detalle_pedido foreign key (idPedido) references Pedidos(idPedido),
     constraint fk_detalle_comida foreign key (idComida) references Comidas(idComida),
    constraint fk_detalle_bebida foreign key (idBebida) references Bebidas(idBebida)
); 


delimiter $$
create procedure sp_agregarCliente(
    in p_nombreCliente varchar(64), 
    in p_apellidoCliente varchar(64), 
    in p_nitCliente varchar(20),
    in p_correoCliente varchar(64),
    in p_contrasenaCliente varchar(64),
    in p_telefonoCliente varchar(20)
)
begin
    insert into Clientes(nombreCliente, apellidoCliente, nitCliente, correoCliente, contrasenaCliente, telefonoCliente)
    values(p_nombreCliente, p_apellidoCliente, p_nitCliente, p_correoCliente, p_contrasenaCliente, p_telefonoCliente);
end$$

create procedure sp_actualizarCliente(
    in p_idCliente int,
    in p_nombreCliente varchar(64), 
    in p_apellidoCliente varchar(64), 
    in p_nitCliente varchar(20),
    in p_correoCliente varchar(64),
    in p_contrasenaCliente varchar(64),
    in p_telefonoCliente varchar(20)
)
begin
    update Clientes
    set nombreCliente = p_nombreCliente,
        apellidoCliente = p_apellidoCliente,
        nitCliente = p_nitCliente,
        correoCliente = p_correoCliente,
        contrasenaCliente = p_contrasenaCliente,
        telefonoCliente = p_telefonoCliente
    where idCliente = p_idCliente;
end$$

create procedure sp_eliminarCliente(in p_idCliente int)
begin
    delete from Clientes where idCliente = p_idCliente;
end$$

create procedure sp_listarClientes()
begin
    select
        idCliente as ID, 
        nombreCliente as NOMBRE, 
        apellidoCliente as APELLIDO, 
        nitCliente as NIT,
        correoCliente as CORREO,
        telefonoCliente as TELEFONO
    from Clientes; 
end$$
delimiter ;

-- CRUD de Empleados
delimiter $$
	create procedure sp_agregarEmpleado(
		in p_nombreEmpleado varchar(64),
		in p_apellidoEmpleado varchar(64),
		in p_puestoEmpleado varchar(64),
		in p_telefonoEmpleado varchar(20),
		in p_correoEmpleado varchar(64), 
		in p_sueldoEmpleado double (10,2))
			begin
				insert into Empleados(nombreEmpleado, apellidoEmpleado, puestoEmpleado, telefonoEmpleado, correoEmpleado, sueldoEmpleado)
				values(p_nombreEmpleado, p_apellidoEmpleado, p_puestoEmpleado, p_telefonoEmpleado, p_correoEmpleado, p_sueldoEmpleado);
			end$$
	delimiter ;

delimiter $$
create procedure sp_actualizarEmpleado(
	in p_idEmpleado int,
	in p_nombreEmpleado varchar(64),
	in p_apellidoEmpleado varchar(64),
	in p_puestoEmpleado varchar(64),
	in p_telefonoEmpleado varchar(20),
	in p_correoEmpleado varchar(64), 
    in p_sueldoEmpleado varchar(64))
		begin
			update Empleados
			set nombreEmpleado = p_nombreEmpleado,
				apellidoEmpleado = p_apellidoEmpleado,
				puestoEmpleado = p_puestoEmpleado,
				telefonoEmpleado = p_telefonoEmpleado,
				correoEmpleado = p_correoEmpleado,
                sueldoEmpleado = p_sueldoEmpleado
			where idEmpleado = p_idEmpleado;
		end$$
delimiter ;

delimiter $$
create procedure sp_eliminarEmpleado(in p_idEmpleado int)
begin
	delete from Empleados where idEmpleado = p_idEmpleado;
end$$
delimiter ;

delimiter $$
create procedure sp_listarEmpleados()
begin
	select
		idEmpleado as ID,
		nombreEmpleado as NOMBRE,
		apellidoEmpleado as APELLIDO,
		puestoEmpleado as PUESTO,
		telefonoEmpleado as TELEFONO,
		correoEmpleado as CORREO,
        sueldoEmpleado as SUELDO
	from Empleados;
end$$
delimiter ;

-- CRUD de Comidas
delimiter $$
create procedure sp_agregarComida(
    in p_nombreComida varchar(250),
    in p_tipo varchar(100),
    in p_precio decimal(6,2)
	)
		begin
			insert into Comidas(nombreComida, tipo, precio)
			values(p_nombreComida, p_tipo, p_precio);
		end$$
delimiter ;

delimiter $$
create procedure sp_actualizarComida(
    in p_idComida int,
    in p_nombreComida varchar(250),
    in p_tipo varchar(100),
    in p_precio decimal(6,2)
)
begin
    update Comidas
    set nombreComida = p_nombreComida,
        tipo = p_tipo,
        precio = p_precio
    where idComida = p_idComida;
end$$
delimiter ;

delimiter $$
create procedure sp_eliminarComida(in p_idComida int)
begin
    delete from Comidas where idComida = p_idComida;
end$$
delimiter ;

delimiter $$
create procedure sp_listarComidas()
begin
    select
        idComida as ID,
        nombreComida as COMIDA,
        tipo as TIPO,
        precio as PRECIO
    from Comidas;
end$$
delimiter ;

-- Procedimientos para Bebidas
delimiter $$
create procedure sp_agregarBebida(
    in p_nombreBebida varchar(250),
    in p_tipo varchar(100),
    in p_precio decimal(6,2)
)
begin
    insert into Bebidas(nombreBebida, tipo, precio)
    values(p_nombreBebida, p_tipo, p_precio);
end$$
delimiter ;

delimiter $$
create procedure sp_actualizarBebida(
    in p_idBebida int,
    in p_nombreBebida varchar(250),
    in p_tipo varchar(100),
    in p_precio decimal(6,2)
	)
		begin
			update Bebidas
			set nombreBebida = p_nombreBebida,
				tipo = p_tipo,
				precio = p_precio,
				ingredientes = p_ingredientes
			where idBebida = p_idBebida;
		end$$
	delimiter ;

delimiter $$
create procedure sp_eliminarBebida(in p_idBebida int)
begin
    delete from Bebidas where idBebida = p_idBebida;
end$$
delimiter ;

delimiter $$
create procedure sp_listarBebidas()
begin
    select
        idBebida as ID,
        nombreBebida as BEBIDA,
        tipo as TIPO,
        precio as PRECIO
    from Bebidas;
end$$
delimiter ;

-- CRUD de pedidos
delimiter $$
create procedure sp_agregarPedido(
	in p_idCliente int,
	in p_idEmpleado int,
	in p_fechaPedido datetime,
	in p_metodoPago enum('Efectivo','Tarjeta'),
	in p_estadoFactura enum('Pagado','Pendiente')
)
begin
	insert into Pedidos(idCliente, idEmpleado, fechaPedido, metodoPago, estadoFactura)
	values(p_idCliente, p_idEmpleado, p_fechaPedido, p_metodoPago, p_estadoFactura);
end$$
delimiter ;

delimiter $$
create procedure sp_actualizarPedido(
	in p_idPedido int,
	in p_idCliente int,
	in p_idEmpleado int,
	in p_fechaPedido datetime,
	in p_metodoPago enum('Efectivo','Tarjeta'),
	in p_estadoFactura enum('Pagado','Pendiente')
)
begin
	update Pedidos
	set idCliente = p_idCliente,
		idEmpleado = p_idEmpleado,
		fechaPedido = p_fechaPedido,
		metodoPago = p_metodoPago,
		estadoFactura = p_estadoFactura
	where idPedido = p_idPedido;
end$$
delimiter ;

delimiter $$
create procedure sp_eliminarPedido(in p_idPedido int)
begin
	delete from Pedidos where idPedido = p_idPedido;
end$$
delimiter ;

delimiter $$
create procedure sp_listarPedidos()
begin
	select
		p.idPedido as ID,
		p.idCliente as CLIENTE,
		p.idEmpleado as EMPLEADO,
		p.fechaPedido as FECHA,
		p.metodoPago as PAGO,
		p.estadoFactura as ESTADO
	from Pedidos p 
    join Clientes c on p.idCliente = c.idCliente
    join Empleados e on p.idEmpleado = e.idEmpleado; 
end$$
delimiter ;


-- CRUD de DetallePedidos

delimiter $$
create procedure sp_agregarDetallePedido(
	in p_idPedido int,
	in p_idComida int,
    in p_idBebida int, 
	in p_cantidad int
)
begin
	insert into DetallePedido(idPedido, idComida, idBebida, cantidad)
	values(p_idPedido, p_idComida, p_idBebida, p_cantidad);
end$$
delimiter ;

delimiter $$
create procedure sp_actualizarDetallePedido(
	in p_idDetalle int,
	in p_idPedido int,
	in p_idComida int,
    in p_ideBebida int, 
	in p_cantidad int
)
begin
	update DetallePedido
	set idPedido = p_idPedido,
		idComida = p_idComida,
        idBebida = p_idBebida, 
		cantidad = p_cantidad
	where idDetalle = p_idDetalle;
end$$
delimiter ;

delimiter $$
create procedure sp_eliminarDetallePedido(in p_idDetalle int)
begin
	delete from DetallePedido where idDetalle = p_idDetalle;
end$$
delimiter ;

delimiter $$
create procedure sp_listarDetallePedidos()
begin
	select
		idDetalle as ID,
		idPedido as PEDIDO,
		idComida as COMIDA,
        idBebida as BEBIDA, 
		cantidad as CANTIDAD
	from DetallePedido;
end$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_verificarLogin(
    IN p_correo VARCHAR(64),
    IN p_contrasena VARCHAR(64)
)
BEGIN
    SELECT * FROM Clientes
    WHERE correoCliente = p_correo AND contrasenaCliente = p_contrasena;
END$$
DELIMITER ;

-- Insertar clientes con login
call sp_agregarCliente('Carlos', 'Pérez', '1234567', 'carlos.perez@email.com', '1234', '5555-0001');
call sp_agregarCliente('María', 'López', '9876543', 'maria.lopez@email.com', 'abcd', '5555-0002');

CALL sp_agregarEmpleado('Luis', 'Pérez', 'Cajero', '5555-1122', 'luis.perez@restaurante.com', 3200.00);
CALL sp_agregarEmpleado('María', 'González', 'Mesera', '5555-2233', 'maria.gonzalez@restaurante.com', 2900.00);

call sp_listarClientes();

CALL sp_agregarComida('Hamburguesa Clásica', 'Principal', 50);
CALL sp_agregarComida('Ensalada César', 'Entrada', 30);
CALL sp_agregarComida('Pizza Pepperoni', 'Principal', 60);
CALL sp_agregarComida('Sopa de Pollo', 'Entrada', 55.50);
CALL sp_agregarComida('Papas Fritas', 'Acompañamiento', 25);
Call sp_agregarBebida('cafe','Caliente',15);
Call sp_agregarBebida('Licuado de fresa','Frio',22.50);
Call sp_agregarBebida('Limonada','frio',18);
Call sp_agregarBebida('capuchino','caliente',23.50);
 
call sp_listarBebidas(); 
Call sp_listarComidas(); 

CALL sp_agregarPedido(1, 2, '2025-07-17 13:45:00', 'Efectivo', 'Pagado');
CALL sp_agregarPedido(2, 1, '2025-07-17 14:10:00', 'Tarjeta', 'Pendiente');
call sp_listarEmpleados();
call sp_listarPedidos();