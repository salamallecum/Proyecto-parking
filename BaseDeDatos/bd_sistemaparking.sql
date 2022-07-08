-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 04-07-2022 a las 22:51:22
-- Versión del servidor: 5.7.36
-- Versión de PHP: 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bd_sistemaparking`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cierres`
--

DROP TABLE IF EXISTS `cierres`;
CREATE TABLE IF NOT EXISTS `cierres` (
  `Id_cierre` int(50) NOT NULL AUTO_INCREMENT,
  `Codigo` int(50) NOT NULL,
  `Fecha_cierre` date NOT NULL,
  `Nombre_usuario` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `Producido` int(30) NOT NULL,
  `Dinero_en_caja` int(30) NOT NULL,
  `Diferencia` int(30) NOT NULL,
  `No_facturas` int(50) NOT NULL,
  `Observaciones` longtext COLLATE utf8_unicode_ci,
  PRIMARY KEY (`Id_cierre`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de cierres de caja';

--
-- Volcado de datos para la tabla `cierres`
--

INSERT INTO `cierres` (`Id_cierre`, `Codigo`, `Fecha_cierre`, `Nombre_usuario`, `Producido`, `Dinero_en_caja`, `Diferencia`, `No_facturas`, `Observaciones`) VALUES
(1, 4444, '1970-01-01', 'NINGUNO', 0, 0, 0, 0, 'Nada'),
(12, 875155543, '2020-10-02', 'Ale97', 11400, 20000, -8600, 1, 'ok'),
(13, 190573786, '2020-10-02', 'Ale97', 28700, 29000, -300, 2, 'cierre ok');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `convenios`
--

DROP TABLE IF EXISTS `convenios`;
CREATE TABLE IF NOT EXISTS `convenios` (
  `Id_convenio` int(50) NOT NULL AUTO_INCREMENT,
  `Nombre_convenio` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Monto` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `Frecuencia` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`Id_convenio`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de convenios de parqueadero';

--
-- Volcado de datos para la tabla `convenios`
--

INSERT INTO `convenios` (`Id_convenio`, `Nombre_convenio`, `Monto`, `Frecuencia`) VALUES
(1, 'NINGUNO', '', ''),
(2, 'ARRIENDO', '300000', 'MENSUAL'),
(3, 'ESCOLTAS', '12000', 'MENSUAL'),
(4, 'BLINDADOS', '2000000', 'TRIMESTRAL');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `facturas`
--

DROP TABLE IF EXISTS `facturas`;
CREATE TABLE IF NOT EXISTS `facturas` (
  `Id_factura` int(50) NOT NULL AUTO_INCREMENT,
  `Codigo` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Fecha_factura` date NOT NULL,
  `Placa` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  `Propietario` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Tipo_vehiculo` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `No_parqueadero` int(50) NOT NULL,
  `Facturado_por` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Estado_fctra` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Contabilizada` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `Id_convenio` int(50) NOT NULL,
  `Id_tarifa` int(50) NOT NULL,
  `Hora_ingreso` datetime NOT NULL,
  `Hora_salida` datetime NOT NULL,
  `Valor_a_pagar` int(50) NOT NULL DEFAULT '0',
  `Efectivo` int(50) NOT NULL DEFAULT '0',
  `Cambio` int(50) NOT NULL DEFAULT '0',
  `Id_cierre` int(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id_factura`),
  KEY `Id_convenio` (`Id_convenio`),
  KEY `Id_tarifa` (`Id_tarifa`),
  KEY `Id_cierre` (`Id_cierre`),
  KEY `No_parqueadero` (`No_parqueadero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de facturas';

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `parqueaderos`
--

DROP TABLE IF EXISTS `parqueaderos`;
CREATE TABLE IF NOT EXISTS `parqueaderos` (
  `Id_parqueadero` int(50) NOT NULL AUTO_INCREMENT,
  `Nombre_parqueadero` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Estado` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `Placa` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Propietario` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Esta_en_parqueadero` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`Id_parqueadero`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de parqueaderos';

--
-- Volcado de datos para la tabla `parqueaderos`
--

INSERT INTO `parqueaderos` (`Id_parqueadero`, `Nombre_parqueadero`, `Estado`, `Placa`, `Propietario`, `Esta_en_parqueadero`) VALUES
(1, '1A', 'Disponible', NULL, NULL, NULL),
(2, '2A', 'Disponible', NULL, NULL, NULL),
(3, '3A', 'Disponible', '', '', ''),
(4, '4A', 'Disponible', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarifas`
--

DROP TABLE IF EXISTS `tarifas`;
CREATE TABLE IF NOT EXISTS `tarifas` (
  `Id_tarifa` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre_tarifa` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Monto_tarifa` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `Frecuencia_tarifa` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Tarifa_anulada` varchar(3) COLLATE utf8_unicode_ci NOT NULL,
  `Tiene_descuento` varchar(3) COLLATE utf8_unicode_ci NOT NULL,
  `Tiempo_descuento` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Unidad_descuento` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Cobrar_Tiempo_Ad` varchar(3) COLLATE utf8_unicode_ci NOT NULL,
  `Monto_Tiempo_Ad` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Unidad_Tiempo_Ad` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`Id_tarifa`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de tarifas';

--
-- Volcado de datos para la tabla `tarifas`
--

INSERT INTO `tarifas` (`Id_tarifa`, `Nombre_tarifa`, `Monto_tarifa`, `Frecuencia_tarifa`, `Tarifa_anulada`, `Tiene_descuento`, `Tiempo_descuento`, `Unidad_descuento`, `Cobrar_Tiempo_Ad`, `Monto_Tiempo_Ad`, `Unidad_Tiempo_Ad`) VALUES
(1, 'NINGUNA', '', '', '', '', NULL, NULL, '', NULL, NULL),
(2, 'TARIF_AUTOMOVIL', '300', 'MINUTO', 'No', 'No', 'null', 'null', 'No', 'null', 'null'),
(3, 'TARIF_MOTO', '150', 'MINUTO', 'No', 'No', NULL, NULL, 'No', NULL, NULL),
(4, 'ESPECIAL', '3600', 'HORA', 'No', 'No', NULL, NULL, 'No', NULL, NULL),
(5, 'PRUEBA TARIF NORMAL MODIF', '6000', 'HORA', 'No', 'No', '', '', 'No', '', ''),
(6, 'PRUEBA TARIFA + ADICIONAL', '4000', 'HORA', 'No', 'No', '', '', 'Si', '500', 'minutos'),
(7, 'PRUEBA ADIC+ MODIF', '65000', 'HORA', 'Si', 'No', '', '', 'Si', '500', 'minutos'),
(8, 'PRUEBA TARIFA CON DESCUENTO', '60000', 'DIA', 'Si', 'Si', '3', 'dias', 'No', '', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE IF NOT EXISTS `usuarios` (
  `Id_usuario` int(30) NOT NULL AUTO_INCREMENT,
  `Nombres` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Apellidos` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Celular` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Telefono` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Usuario` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Clave` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Rol` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Activo` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`Id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de usuarios';

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`Id_usuario`, `Nombres`, `Apellidos`, `Celular`, `Telefono`, `Usuario`, `Clave`, `Rol`, `Activo`) VALUES
(8, 'Alejo', 'estoy modificANDO', '12345', '12345', 'Morita', 'lalala', 'Administra', 'No'),
(25, 'ANA REUTILIA', 'TORRES SARMIENTO', '3186304464', '12345', 'ANA MARIA', '12345', 'Usuario', 'No'),
(27, 'WILFREDI', 'AMAYA TORRES', '3112236781', '4897894', 'More', 'm0r3**489', 'Administra', 'No'),
(28, 'LUIS ALEJANDRO', 'AMAYA TORRES', '3156025903', '55555', 'Alejo97', '18abril', 'Administra', 'Si'),
(29, 'MARIA', 'DE LOS PERROS', '2345', '56789', 'MARIA', '1736', 'Usuario', 'Si'),
(30, 'MARIA ARACELY', 'GALINDO JIMENEZ', '3156025903', '4444444444', 'Arace', '12345', 'Usuario', 'Si');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculos`
--

DROP TABLE IF EXISTS `vehiculos`;
CREATE TABLE IF NOT EXISTS `vehiculos` (
  `Id_vehiculo` int(50) NOT NULL AUTO_INCREMENT,
  `Placa` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  `Propietario` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Clase` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Id_parqueadero` int(50) NOT NULL,
  `Id_convenio` int(50) NOT NULL,
  `Id_tarifa` int(50) DEFAULT NULL,
  PRIMARY KEY (`Id_vehiculo`),
  KEY `Id_convenio` (`Id_convenio`),
  KEY `Id_tarifa` (`Id_tarifa`),
  KEY `Id_parqueadero` (`Id_parqueadero`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de vehiculos';

--
-- Volcado de datos para la tabla `vehiculos`
--

INSERT INTO `vehiculos` (`Id_vehiculo`, `Placa`, `Propietario`, `Clase`, `Id_parqueadero`, `Id_convenio`, `Id_tarifa`) VALUES
(1, 'CSA503', 'LUIS AMAYA', 'AUTOMOVIL', 1, 1, 2),
(2, 'BJG978', 'WILFREDI AMAYA', 'AUTOMOVIL', 2, 1, 2),
(3, 'BTA824', 'MARIS AMAYA', 'AUTOMOVIL', 3, 1, 2);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `facturas`
--
ALTER TABLE `facturas`
  ADD CONSTRAINT `facturas_ibfk_1` FOREIGN KEY (`Id_convenio`) REFERENCES `convenios` (`Id_convenio`) ON UPDATE CASCADE,
  ADD CONSTRAINT `facturas_ibfk_2` FOREIGN KEY (`Id_tarifa`) REFERENCES `tarifas` (`Id_tarifa`) ON UPDATE CASCADE,
  ADD CONSTRAINT `facturas_ibfk_3` FOREIGN KEY (`Id_cierre`) REFERENCES `cierres` (`Id_cierre`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `facturas_ibfk_4` FOREIGN KEY (`No_parqueadero`) REFERENCES `parqueaderos` (`Id_parqueadero`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `vehiculos`
--
ALTER TABLE `vehiculos`
  ADD CONSTRAINT `vehiculos_ibfk_1` FOREIGN KEY (`Id_tarifa`) REFERENCES `tarifas` (`Id_tarifa`) ON UPDATE CASCADE,
  ADD CONSTRAINT `vehiculos_ibfk_2` FOREIGN KEY (`Id_convenio`) REFERENCES `convenios` (`Id_convenio`) ON UPDATE CASCADE,
  ADD CONSTRAINT `vehiculos_ibfk_3` FOREIGN KEY (`Id_parqueadero`) REFERENCES `parqueaderos` (`Id_parqueadero`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
