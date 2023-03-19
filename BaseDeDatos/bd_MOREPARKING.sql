-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 19-03-2023 a las 02:22:58
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
-- Estructura de tabla para la tabla `arqueos`
--

DROP TABLE IF EXISTS `arqueos`;
CREATE TABLE IF NOT EXISTS `arqueos` (
  `Id_arqueo` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `fecha_arqueo` datetime NOT NULL,
  `usuario` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `base_caja` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `numerobilletes100mil` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `numerobilletes50mil` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `numerobilletes20mil` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `numerobilletes10mil` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `numerobilletes5mil` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `numerobilletes2mil` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `numerobilletesMil` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `numeromonedas500` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `numeromonedas200` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `numeromonedas100` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `numeromonedas50` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `montoen100mil` int(20) NOT NULL,
  `montoen50mil` int(20) NOT NULL,
  `montoen20mil` int(20) NOT NULL,
  `montoen10mil` int(20) NOT NULL,
  `montoen5mil` int(20) NOT NULL,
  `montoen2mil` int(20) NOT NULL,
  `montoenmil` int(20) NOT NULL,
  `montoen500` int(20) NOT NULL,
  `montoen200` int(20) NOT NULL,
  `montoen100` int(20) NOT NULL,
  `montoen50` int(11) NOT NULL,
  `total_caja` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `diferencia` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`Id_arqueo`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de arqueos realizados a la caja';

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cierres`
--

DROP TABLE IF EXISTS `cierres`;
CREATE TABLE IF NOT EXISTS `cierres` (
  `Id_cierre` int(50) NOT NULL AUTO_INCREMENT,
  `Codigo` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Codigo_arqueo` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Fecha_cierre` datetime NOT NULL,
  `Nombre_usuario` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Base_caja` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `numerobilletes100mil` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `numerobilletes50mil` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `numerobilletes20mil` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `numerobilletes10mil` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `numerobilletes5mil` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `numerobilletes2mil` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `numerobilletesMil` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `numeromonedas500` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `numeromonedas200` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `numeromonedas100` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `numeromonedas50` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `montoen100mil` int(20) NOT NULL,
  `montoen50mil` int(20) NOT NULL,
  `montoen20mil` int(20) NOT NULL,
  `montoen10mil` int(20) NOT NULL,
  `montoen5mil` int(20) NOT NULL,
  `montoen2mil` int(20) NOT NULL,
  `montoenmil` int(20) NOT NULL,
  `montoen500` int(20) NOT NULL,
  `montoen200` int(20) NOT NULL,
  `montoen100` int(20) NOT NULL,
  `montoen50` int(20) NOT NULL,
  `Producido` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Total_esperado` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Dinero_en_caja` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Diferencia` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Dinero_a_consignar` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `No_facturas` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Observaciones` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`Id_cierre`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de cierres de caja';

--
-- Volcado de datos para la tabla `cierres`
--

INSERT INTO `cierres` (`Id_cierre`, `Codigo`, `Codigo_arqueo`, `Fecha_cierre`, `Nombre_usuario`, `Base_caja`, `numerobilletes100mil`, `numerobilletes50mil`, `numerobilletes20mil`, `numerobilletes10mil`, `numerobilletes5mil`, `numerobilletes2mil`, `numerobilletesMil`, `numeromonedas500`, `numeromonedas200`, `numeromonedas100`, `numeromonedas50`, `montoen100mil`, `montoen50mil`, `montoen20mil`, `montoen10mil`, `montoen5mil`, `montoen2mil`, `montoenmil`, `montoen500`, `montoen200`, `montoen100`, `montoen50`, `Producido`, `Total_esperado`, `Dinero_en_caja`, `Diferencia`, `Dinero_a_consignar`, `No_facturas`, `Observaciones`) VALUES
(1, '4444', '', '1970-01-01 00:00:00', '', '000000', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '00', '00', '00', '00', '00', '0', 'Nada / cierre default');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `convenios`
--

DROP TABLE IF EXISTS `convenios`;
CREATE TABLE IF NOT EXISTS `convenios` (
  `Id_convenio` int(50) NOT NULL AUTO_INCREMENT,
  `Nombre_convenio` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Monto` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Frecuencia` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`Id_convenio`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de convenios de parqueadero';

--
-- Volcado de datos para la tabla `convenios`
--

INSERT INTO `convenios` (`Id_convenio`, `Nombre_convenio`, `Monto`, `Frecuencia`) VALUES
(1, 'NINGUNO', '', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `facturas`
--

DROP TABLE IF EXISTS `facturas`;
CREATE TABLE IF NOT EXISTS `facturas` (
  `Id_factura` int(50) NOT NULL AUTO_INCREMENT,
  `Codigo` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Id_cierre` int(50) NOT NULL DEFAULT '0',
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
  `Hora_ingreso` datetime DEFAULT NULL,
  `Hora_salida` datetime DEFAULT NULL,
  `Diferencia` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Valor_a_pagar` varchar(50) COLLATE utf8_unicode_ci DEFAULT '0',
  `Efectivo` varchar(50) COLLATE utf8_unicode_ci DEFAULT '0',
  `Cambio` varchar(50) COLLATE utf8_unicode_ci DEFAULT '0',
  PRIMARY KEY (`Id_factura`),
  KEY `Id_convenio` (`Id_convenio`),
  KEY `Id_tarifa` (`Id_tarifa`),
  KEY `Id_cierre` (`Id_cierre`),
  KEY `No_parqueadero` (`No_parqueadero`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de facturas';

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `parametros`
--

DROP TABLE IF EXISTS `parametros`;
CREATE TABLE IF NOT EXISTS `parametros` (
  `Id_parametro` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre_parametro` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Valor` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`Id_parametro`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de parámetros del sistema';

--
-- Volcado de datos para la tabla `parametros`
--

INSERT INTO `parametros` (`Id_parametro`, `Nombre_parametro`, `Valor`) VALUES
(1, 'BASE_CAJA', '400000');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `parqueaderos`
--

DROP TABLE IF EXISTS `parqueaderos`;
CREATE TABLE IF NOT EXISTS `parqueaderos` (
  `Id_parqueadero` int(50) NOT NULL AUTO_INCREMENT,
  `Nombre_parqueadero` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `TipoParq` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Estado` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `Placa` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  `Propietario` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Esta_en_parqueadero` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`Id_parqueadero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de parqueaderos';

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarifas`
--

DROP TABLE IF EXISTS `tarifas`;
CREATE TABLE IF NOT EXISTS `tarifas` (
  `Id_tarifa` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre_tarifa` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Monto_tarifa` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Frecuencia_tarifa` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Tarifa_anulada` varchar(3) COLLATE utf8_unicode_ci NOT NULL,
  `Tiene_descuento` varchar(3) COLLATE utf8_unicode_ci NOT NULL,
  `Tiempo_descuento` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Unidad_descuento` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Cobrar_Tiempo_Ad` varchar(3) COLLATE utf8_unicode_ci NOT NULL,
  `Monto_Tiempo_Ad` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Unidad_Tiempo_Ad` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`Id_tarifa`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de tarifas';

--
-- Volcado de datos para la tabla `tarifas`
--

INSERT INTO `tarifas` (`Id_tarifa`, `Nombre_tarifa`, `Monto_tarifa`, `Frecuencia_tarifa`, `Tarifa_anulada`, `Tiene_descuento`, `Tiempo_descuento`, `Unidad_descuento`, `Cobrar_Tiempo_Ad`, `Monto_Tiempo_Ad`, `Unidad_Tiempo_Ad`) VALUES
(1, 'NINGUNA', '0', '', '', '', NULL, NULL, '', NULL, NULL),
(2, 'TARIF_AUTOMOVIL', '300', 'MINUTO', 'No', 'No', 'null', 'null', 'No', 'null', 'null'),
(3, 'TARIF_MOTO', '150', 'MINUTO', 'No', 'No', NULL, NULL, 'No', NULL, NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de usuarios';

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`Id_usuario`, `Nombres`, `Apellidos`, `Celular`, `Telefono`, `Usuario`, `Clave`, `Rol`, `Activo`) VALUES
(1, 'ING  ALEJO', 'AMAYA TORRES', '3147427981', '3147427981', 'Alejo97', '18abril', 'Administra', 'Si'),
(2, 'WILFREDI', 'AMAYA TORRES', '3112236781', '4897894', 'More', 'm0r3**', 'Administra', 'Si');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de vehiculos';

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
