-- MariaDB dump 10.19  Distrib 10.4.24-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: bd_sistemaparking
-- ------------------------------------------------------
-- Server version	5.7.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `arqueos`
--

DROP TABLE IF EXISTS `arqueos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `arqueos` (
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `arqueos`
--

LOCK TABLES `arqueos` WRITE;
/*!40000 ALTER TABLE `arqueos` DISABLE KEYS */;
/*!40000 ALTER TABLE `arqueos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cierres`
--

DROP TABLE IF EXISTS `cierres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cierres` (
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cierres`
--

LOCK TABLES `cierres` WRITE;
/*!40000 ALTER TABLE `cierres` DISABLE KEYS */;
INSERT INTO `cierres` VALUES (1,'4444','','1970-01-01 00:00:00','','000000','0','0','0','0','0','0','0','0','0','0','0',0,0,0,0,0,0,0,0,0,0,0,'00','00','00','00','00','0','Nada / cierre default');
/*!40000 ALTER TABLE `cierres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `convenios`
--

DROP TABLE IF EXISTS `convenios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `convenios` (
  `Id_convenio` int(50) NOT NULL AUTO_INCREMENT,
  `Nombre_convenio` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Monto` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Frecuencia` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`Id_convenio`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de convenios de parqueadero';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `convenios`
--

LOCK TABLES `convenios` WRITE;
/*!40000 ALTER TABLE `convenios` DISABLE KEYS */;
INSERT INTO `convenios` VALUES (1,'NINGUNO','','');
/*!40000 ALTER TABLE `convenios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facturas`
--

DROP TABLE IF EXISTS `facturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `facturas` (
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
  KEY `No_parqueadero` (`No_parqueadero`),
  CONSTRAINT `facturas_ibfk_1` FOREIGN KEY (`Id_convenio`) REFERENCES `convenios` (`Id_convenio`) ON UPDATE CASCADE,
  CONSTRAINT `facturas_ibfk_2` FOREIGN KEY (`Id_tarifa`) REFERENCES `tarifas` (`Id_tarifa`) ON UPDATE CASCADE,
  CONSTRAINT `facturas_ibfk_3` FOREIGN KEY (`Id_cierre`) REFERENCES `cierres` (`Id_cierre`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `facturas_ibfk_4` FOREIGN KEY (`No_parqueadero`) REFERENCES `parqueaderos` (`Id_parqueadero`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de facturas';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facturas`
--

LOCK TABLES `facturas` WRITE;
/*!40000 ALTER TABLE `facturas` DISABLE KEYS */;
/*!40000 ALTER TABLE `facturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parametros`
--

DROP TABLE IF EXISTS `parametros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parametros` (
  `Id_parametro` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre_parametro` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Valor` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`Id_parametro`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de parámetros del sistema';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parametros`
--

LOCK TABLES `parametros` WRITE;
/*!40000 ALTER TABLE `parametros` DISABLE KEYS */;
INSERT INTO `parametros` VALUES (1,'BASE_CAJA','400000');
/*!40000 ALTER TABLE `parametros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parqueaderos`
--

DROP TABLE IF EXISTS `parqueaderos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parqueaderos` (
  `Id_parqueadero` int(50) NOT NULL AUTO_INCREMENT,
  `Nombre_parqueadero` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `TipoParq` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Estado` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `Placa` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  `Propietario` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Esta_en_parqueadero` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`Id_parqueadero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de parqueaderos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parqueaderos`
--

LOCK TABLES `parqueaderos` WRITE;
/*!40000 ALTER TABLE `parqueaderos` DISABLE KEYS */;
/*!40000 ALTER TABLE `parqueaderos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarifas`
--

DROP TABLE IF EXISTS `tarifas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tarifas` (
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarifas`
--

LOCK TABLES `tarifas` WRITE;
/*!40000 ALTER TABLE `tarifas` DISABLE KEYS */;
INSERT INTO `tarifas` VALUES (1,'NINGUNA','0','','','',NULL,NULL,'',NULL,NULL),(2,'TARIF_AUTOMOVIL','300','MINUTO','No','No','null','null','No','null','null'),(3,'TARIF_MOTO','150','MINUTO','No','No',NULL,NULL,'No',NULL,NULL);
/*!40000 ALTER TABLE `tarifas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'ING  ALEJO','AMAYA TORRES','3147427981','3147427981','Alejo97','18abril','Administra','Si'),(2,'WILFREDI','AMAYA TORRES','3112236781','4897894','More','m0r3**','Administra','Si');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehiculos`
--

DROP TABLE IF EXISTS `vehiculos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehiculos` (
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
  KEY `Id_parqueadero` (`Id_parqueadero`),
  CONSTRAINT `vehiculos_ibfk_1` FOREIGN KEY (`Id_tarifa`) REFERENCES `tarifas` (`Id_tarifa`) ON UPDATE CASCADE,
  CONSTRAINT `vehiculos_ibfk_2` FOREIGN KEY (`Id_convenio`) REFERENCES `convenios` (`Id_convenio`) ON UPDATE CASCADE,
  CONSTRAINT `vehiculos_ibfk_3` FOREIGN KEY (`Id_parqueadero`) REFERENCES `parqueaderos` (`Id_parqueadero`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tabla de vehiculos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehiculos`
--

LOCK TABLES `vehiculos` WRITE;
/*!40000 ALTER TABLE `vehiculos` DISABLE KEYS */;
/*!40000 ALTER TABLE `vehiculos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-24 20:12:29
