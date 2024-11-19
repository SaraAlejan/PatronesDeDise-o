<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulario para llenar tablas de la base de datos</title>
</head>
<body>

<h2>Formulario para insertar datos en la base de datos</h2>

<?php
// Configuración de la base de datos
$servername = "localhost";
$username = "root"; // Cambia esto por tu usuario de MySQL
$password = ""; // Cambia esto por tu contraseña de MySQL
$database = "patrones"; // Cambia esto por el nombre de tu base de datos

// Crear conexión
$conn = new mysqli($servername, $username, $password, $database);

// Verificar conexión
if ($conn->connect_error) {
    die("Conexión fallida: " . $conn->connect_error);
}

// Procesar los datos del formulario
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $sql_cliente = "";
    $sql_habitacion = "";
    $sql_reserva = "";
    $sql_pago = "";
    $sql_empleado = "";
    $sql_servicio = "";
    $sql_reserva_servicio = "";

    // Agregar Cliente
    if (!empty($_POST['cliente_nombre'])) {
        $nombre = $_POST['cliente_nombre'];
        $apellido = $_POST['cliente_apellido'];
        $email = $_POST['cliente_email'];
        $telefono = $_POST['cliente_telefono'];
        $direccion = $_POST['cliente_direccion'];
        $sql_cliente = "INSERT INTO Clientes (nombre, apellido, email, telefono, direccion) 
                        VALUES ('$nombre', '$apellido', '$email', '$telefono', '$direccion')";
    }

    // Agregar Habitación
    if (!empty($_POST['habitacion_numero'])) {
        $numero = $_POST['habitacion_numero'];
        $tipo = $_POST['habitacion_tipo'];
        $precio = $_POST['habitacion_precio'];
        $estado = $_POST['habitacion_estado'];
        $sql_habitacion = "INSERT INTO Habitaciones (numero, tipo, precio_por_noche, estado) 
                           VALUES ('$numero', '$tipo', '$precio', '$estado')";
    }

    // Agregar Reserva
    if (!empty($_POST['reserva_cliente_id'])) {
        $cliente_id = $_POST['reserva_cliente_id'];
        $habitacion_id = $_POST['reserva_habitacion_id'];
        $fecha_checkin = $_POST['reserva_checkin'];
        $fecha_checkout = $_POST['reserva_checkout'];
        $estado = $_POST['reserva_estado'];
        $sql_reserva = "INSERT INTO Reservas (cliente_id, habitacion_id, fecha_checkin, fecha_checkout, estado) 
                        VALUES ('$cliente_id', '$habitacion_id', '$fecha_checkin', '$fecha_checkout', '$estado')";
    }

    // Agregar Pago
    if (!empty($_POST['pago_reserva_id'])) {
        $reserva_id = $_POST['pago_reserva_id'];
        $monto = $_POST['pago_monto'];
        $metodo = $_POST['pago_metodo'];
        $sql_pago = "INSERT INTO Pagos (reserva_id, monto, metodo_pago) 
                     VALUES ('$reserva_id', '$monto', '$metodo')";
    }

    // Agregar Empleado
    if (!empty($_POST['empleado_nombre'])) {
        $nombre = $_POST['empleado_nombre'];
        $apellido = $_POST['empleado_apellido'];
        $cargo = $_POST['empleado_cargo'];
        $email = $_POST['empleado_email'];
        $telefono = $_POST['empleado_telefono'];
        $sql_empleado = "INSERT INTO Empleados (nombre, apellido, cargo, email, telefono) 
                         VALUES ('$nombre', '$apellido', '$cargo', '$email', '$telefono')";
    }

    // Agregar Servicio Adicional
    if (!empty($_POST['servicio_nombre'])) {
        $nombre_servicio = $_POST['servicio_nombre'];
        $precio = $_POST['servicio_precio'];
        $descripcion = $_POST['servicio_descripcion'];
        $sql_servicio = "INSERT INTO Servicios_Adicionales (nombre_servicio, precio, descripcion) 
                         VALUES ('$nombre_servicio', '$precio', '$descripcion')";
    }

    // Agregar Reserva Servicio
    if (!empty($_POST['reserva_servicio_reserva_id'])) {
        $reserva_id = $_POST['reserva_servicio_reserva_id'];
        $servicio_id = $_POST['reserva_servicio_servicio_id'];
        $sql_reserva_servicio = "INSERT INTO Reserva_Servicio (reserva_id, servicio_id) 
                                 VALUES ('$reserva_id', '$servicio_id')";
    }

    // Ejecutar las consultas
    $queries = [$sql_cliente, $sql_habitacion, $sql_reserva, $sql_pago, $sql_empleado, $sql_servicio, $sql_reserva_servicio];
    foreach ($queries as $query) {
        if ($query && $conn->query($query) === TRUE) {
            echo "<p>Registro agregado correctamente</p>";
        } elseif ($query) {
            echo "<p>Error al agregar el registro: " . $conn->error . "</p>";
        }
    }
}

$conn->close();
?>

<!-- Formulario único para agregar todos los datos -->
<form method="POST">
    <h3>Agregar Cliente</h3>
    <label for="cliente_nombre">Nombre:</label>
    <input type="text" id="cliente_nombre" name="cliente_nombre" required>
    <label for="cliente_apellido">Apellido:</label>
    <input type="text" id="cliente_apellido" name="cliente_apellido" required>
    <label for="cliente_email">Email:</label>
    <input type="email" id="cliente_email" name="cliente_email" required>
    <label for="cliente_telefono">Teléfono:</label>
    <input type="text" id="cliente_telefono" name="cliente_telefono">
    <label for="cliente_direccion">Dirección:</label>
    <input type="text" id="cliente_direccion" name="cliente_direccion">

    <h3>Agregar Habitación</h3>
    <label for="habitacion_numero">Número de Habitación:</label>
    <input type="text" id="habitacion_numero" name="habitacion_numero" required>
    <label for="habitacion_tipo">Tipo:</label>
    <input type="text" id="habitacion_tipo" name="habitacion_tipo" required>
    <label for="habitacion_precio">Precio por Noche:</label>
    <input type="number" step="0.01" id="habitacion_precio" name="habitacion_precio" required>
    <label for="habitacion_estado">Estado:</label>
    <select id="habitacion_estado" name="habitacion_estado">
        <option value="Disponible">Disponible</option>
        <option value="Ocupada">Ocupada</option>
        <option value="Mantenimiento">Mantenimiento</option>
    </select>

    <h3>Agregar Reserva</h3>
    <label for="reserva_cliente_id">ID del Cliente:</label>
    <input type="number" id="reserva_cliente_id" name="reserva_cliente_id" required>
    <label for="reserva_habitacion_id">ID de la Habitación:</label>
    <input type="number" id="reserva_habitacion_id" name="reserva_habitacion_id" required>
    <label for="reserva_checkin">Fecha de Check-In:</label>
    <input type="date" id="reserva_checkin" name="reserva_checkin" required>
    <label for="reserva_checkout">Fecha de Check-Out:</label>
    <input type="date" id="reserva_checkout" name="reserva_checkout" required>
    <label for="reserva_estado">Estado:</label>
    <select id="reserva_estado" name="reserva_estado">
        <option value="Confirmada">Confirmada</option>
        <option value="Cancelada">Cancelada</option>
        <option value="Finalizada">Finalizada</option>
    </select>

    <h3>Agregar Pago</h3>
    <label for="pago_reserva_id">ID de la Reserva:</label>
    <input type="number" id="pago_reserva_id" name="pago_reserva_id" required>
    <label for="pago_monto">Monto:</label>
    <input type="number" step="0.01" id="pago_monto" name="pago_monto" required>
    <label for="pago_metodo">Método de Pago:</label>
    <select id="pago_metodo" name="pago_metodo">
        <option value="Tarjeta de crédito">Tarjeta de crédito</option>
        <option value="PayPal">PayPal</option>
        <option value="Efectivo">Efectivo</option>
    </select>

    <h3>Agregar Empleado</h3>
    <label for="empleado_nombre">Nombre:</label>
    <input type="text" id="empleado_nombre" name="empleado_nombre" required>
    <label for="empleado_apellido">Apellido:</label>
    <input type="text" id="empleado_apellido" name="empleado_apellido" required>
    <label for="empleado_cargo">Cargo:</label>
    <input type="text" id="empleado_cargo" name="empleado_cargo" required>
    <label for="empleado_email">Email:</label>
    <input type="email" id="empleado_email" name="empleado_email" required>
    <label for="empleado_telefono">Teléfono:</label>
    <input type="text" id="empleado_telefono" name="empleado_telefono">

    <h3>Agregar Servicio Adicional</h3>
    <label for="servicio_nombre">Nombre del Servicio:</label>
    <input type="text" id="servicio_nombre" name="servicio_nombre" required>
    <label for="servicio_precio">Precio:</label>
    <input type="number" step="0.01" id="servicio_precio" name="servicio_precio" required>
    <label for="servicio_descripcion">Descripción:</label>
    <textarea id="servicio_descripcion" name="servicio_descripcion"></textarea>

    <h3>Agregar Reserva Servicio</h3>
    <label for="reserva_servicio_reserva_id">ID de la Reserva:</label>
    <input type="number" id="reserva_servicio_reserva_id" name="reserva_servicio_reserva_id" required>
    <label for="reserva_servicio_servicio_id">ID del Servicio:</label>
    <input type="number" id="reserva_servicio_servicio_id" name="reserva_servicio_servicio_id" required>

    <button type="submit">Registrar Todo</button>
</form>

</body>
</html>
