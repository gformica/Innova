--Archivo: INNOVA_schema.sql
--Contenido: Secuencia de comandos SQL para la creacion de la base de datos 
--relacional de INNOVA. 
--Autores: 09-10910 - Patricia Wilthew, 10-11036 - Gabriel Fromica, Melecio Ponte

CREATE TABLE CLIENTE(
	id_cliente		VARCHAR(20)	NOT NULL,
	nomb_cliente		VARCHAR(50)	NOT NULL,
	nac_cliente		DATE		NOT NULL,
	dir_cliente		TEXT		NOT NULL,
	CONSTRAINT PK_CLIENTE PRIMARY KEY (id_cliente),
	CONSTRAINT VALID_ID_CLIENTE
		CHECK (id_cliente LIKE 'V-%' OR	id_cliente LIKE 'E-%'),
	CONSTRAINT VALID_NAC_CLIENTE CHECK (nac_cliente < CURRENT_DATE)
);

CREATE TABLE TARJETA(
	nro_tarjeta		BIGINT		NOT NULL,
	tipo_tarjeta		CHAR		NOT NULL,
	banc_tarjeta		VARCHAR(20)	NOT NULL,
	ci_tarjeta		VARCHAR(20)	NOT NULL,
	venc_tarjeta		DATE		NOT NULL,
	CONSTRAINT PK_TARJETA PRIMARY KEY (nro_tarjeta),
	CONSTRAINT VALID_NRO_TARJETA CHECK (nro_tarjeta >= 0),
	CONSTRAINT VALID_TIPO_TARJETA CHECK (tipo_tarjeta IN ('C', 'D')),
	CONSTRAINT VALID_CI_TARJETA 
		CHECK (ci_tarjeta LIKE 'V-%' OR	ci_tarjeta LIKE 'E-%'),
	CONSTRAINT VALID_VENC_TARJETA CHECK (CURRENT_DATE < venc_tarjeta)
);

CREATE TABLE SERVICIO(
	id_servicio		VARCHAR(20)	NOT NULL,
	nomb_servicio		VARCHAR(50)	NOT NULL,
	desc_servicio		TEXT,
	monto			NUMERIC		NOT NULL,
	CONSTRAINT PK_SERVICIO PRIMARY KEY (id_servicio)
);

CREATE TABLE PAQUETE(
	id_paquete		VARCHAR(20)	NOT NULL,
	nomb_paquete		VARCHAR(50)	NOT NULL,
	--Si monto_paquete es 0, es parte de un plan. Si no, es un servicio de renta
	monto_paquete		NUMERIC		NOT NULL, 
	desc_paquete		TEXT,
	tipo_paquete		VARCHAR(10) NOT NULL,
	CONSTRAINT PK_PAQUETE PRIMARY KEY (ID_PAQUETE),
	CONSTRAINT VALID_MONTO_PAQUETE CHECK (MONTO_PAQUETE >= 0),
	CONSTRAINT VALID_TIPO_PAQUETE CHECK 
		(tipo_paquete IN ('prepago', 'postpago', 'ambos', 'niguno'))
	
);

CREATE TABLE PLAN(
	id_plan			VARCHAR(20)	NOT NULL,
	nomb_plan		VARCHAR(50)	NOT NULL, 
        monto_plan		NUMERIC		NOT NULL,
	tipo_plan		VARCHAR(10)	NOT NULL, 
	desc_plan		TEXT,
	CONSTRAINT PK_PLAN PRIMARY KEY (id_plan),
	CONSTRAINT VALID_MONTO_PLAN CHECK (monto_plan >= 0),
	CONSTRAINT VALID_TIPO_PLAN CHECK (tipo_plan IN ('prepago','postpago'))
);

CREATE TABLE PRODUCTO(
	nomb_producto			VARCHAR(30)	NOT NULL,
	id_producto			VARCHAR(20)	NOT NULL,
	id_cliente			VARCHAR(20)	NOT NULL,
	saldo				NUMERIC		NOT NULL,
	CONSTRAINT PK_PRODUCTO PRIMARY KEY (id_producto),
	CONSTRAINT FK_CLIENTE_PRODUCTO FOREIGN KEY (id_cliente)
		REFERENCES CLIENTE (id_cliente)
	
);

CREATE TABLE FACTURA(
	nro_factura		INTEGER		NOT NULL,
	id_producto		VARCHAR(20)	NOT NULL,
	fecha_factura		DATE		NOT NULL,
	monto_factura		NUMERIC		NOT NULL,
	pagada_factura		BOOLEAN		NOT NULL,
	nro_tarjeta		BIGINT,	-- PUEDE SER NULL
	obs_factura		TEXT,
	CONSTRAINT PK_FACTURA PRIMARY KEY (id_producto, fecha_factura),
	CONSTRAINT FK_TARJETA_FACTURA FOREIGN KEY (nro_tarjeta)
		REFERENCES TARJETA (nro_tarjeta),
	CONSTRAINT FK_PRODUCTO_FACTURA FOREIGN KEY (id_producto)
		REFERENCES PRODUCTO (id_producto),
	CONSTRAINT VALID_NRO_FACTURA CHECK (nro_factura >= 0),
	CONSTRAINT VALID_MONTO_FACTURA CHECK (monto_factura >= 0),
	CONSTRAINT VALID_FECHA_FACTURA CHECK (fecha_factura <= CURRENT_DATE)
);

CREATE TABLE CONSUMO(
	id_producto		VARCHAR(20)	NOT NULL,
	id_servicio		VARCHAR(20)	NOT NULL,
	fecha_consumo		TIMESTAMP	NOT NULL,
	--si cant_consumo y cant_total_consumo = 0, entonces se renovaron los
	--servicios
	cant_consumo		INTEGER		NOT NULL,
	cant_total_consumo	INTEGER		NOT NULL,
	CONSTRAINT PK_CONSUMO PRIMARY KEY (id_producto, id_servicio,
		fecha_consumo),
	CONSTRAINT FK_PRODUCTO_CONSUMO FOREIGN KEY (id_producto)
		REFERENCES PRODUCTO (id_producto),
	CONSTRAINT FK_SERVICIO_CONSUMO FOREIGN KEY (id_servicio)
		REFERENCES SERVICIO (id_servicio),
	CONSTRAINT VALID_FECHA_CONSUMO CHECK (fecha_consumo <= CURRENT_DATE),
	CONSTRAINT VALID_DURACION_CONSUMO CHECK (cant_consumo >= 0)
);

CREATE TABLE CONFORMA(
	id_paquete		VARCHAR(20)	NOT NULL,
	id_servicio		VARCHAR(20)	NOT NULL,
	cant_conforma		INTEGER		NOT NULL,
	cost_adic_conforma	NUMERIC		NOT NULL,    -- Solo para post-pago, pre-pago = 0
	CONSTRAINT PK_CONFORMA PRIMARY KEY (id_paquete, id_servicio),
	CONSTRAINT FK_PAQUETE_CONFORMA FOREIGN KEY (id_paquete)
		REFERENCES PAQUETE (id_paquete),
	CONSTRAINT FK_SERVICIO_CONFORMA FOREIGN KEY (id_servicio)
		REFERENCES SERVICIO (id_servicio),
	CONSTRAINT VALID_CANT_CONFORMA CHECK (cant_conforma > 0),
	CONSTRAINT VALID_COST_ADIC_CONFORMA CHECK (cost_adic_conforma >= 0)
);

CREATE TABLE POSEE(
	id_plan			VARCHAR(20)	NOT NULL,
	id_paquete		VARCHAR(20)	NOT NULL,
	CONSTRAINT PK_POSEE PRIMARY KEY (id_plan, id_paquete),
	CONSTRAINT FK_PLAN_POSEE FOREIGN KEY (id_plan)
		REFERENCES PLAN (id_plan),
	CONSTRAINT FK_PAQUETE_POSEE FOREIGN KEY (id_paquete)
		REFERENCES PAQUETE (id_paquete)
);

CREATE TABLE AGREGA(
	id_producto		VARCHAR(20)	NOT NULL,
	id_paquete		VARCHAR(20)	NOT NULL,
	fecha_agrega		DATE 		NOT NULL,
	vigente_agrega		BOOLEAN		NOT NULL,
	CONSTRAINT PK_AGREGA PRIMARY KEY (id_producto, id_paquete, fecha_agrega),
	CONSTRAINT FK_PRODUCTO_AGREGA FOREIGN KEY (id_producto)
		REFERENCES PRODUCTO (id_producto),
	CONSTRAINT FK_PAQUETE_AGREGA FOREIGN KEY (id_paquete)
		REFERENCES PAQUETE (id_paquete)
);

CREATE TABLE AFILIA(
	id_producto		VARCHAR(20) NOT NULL,
	id_plan			VARCHAR(20) NOT NULL,
	fecha_afilia		DATE NOT NULL,
	dia_cobro		INTEGER NOT NULL,
	vigente_afilia		BOOLEAN		NOT NULL,	
	CONSTRAINT PK_AFILIA
		PRIMARY KEY (id_producto, id_plan, fecha_afilia),
	CONSTRAINT FK_PRODUCTO_AFILIA FOREIGN KEY (id_producto)
		REFERENCES PRODUCTO (id_producto),
	CONSTRAINT FK_PLAN_AFILIA FOREIGN KEY (id_plan)
		REFERENCES PLAN (id_plan),
	CONSTRAINT VALID_FECHA_AFILIA CHECK (fecha_afilia <= CURRENT_DATE),
	CONSTRAINT VALID_DIA_COBRO CHECK ((dia_cobro >= 1) and (dia_cobro <= 28))
);

CREATE TABLE ADICIONA(
	id_producto		VARCHAR(20) 	NOT NULL,
	id_servicio		VARCHAR(20)	NOT NULL,
	fecha_adicion		DATE 		NOT NULL,
	vigente_adiciona	BOOLEAN		NOT NULL,
	CONSTRAINT PK_ADICIONA
		PRIMARY KEY (id_producto, id_servicio, fecha_adicion),
	CONSTRAINT FK_PRODUCTO_ADICIONA FOREIGN KEY (id_producto)
		REFERENCES PRODUCTO (id_producto),
	CONSTRAINT FK_SERVICIO_ADICIONA FOREIGN KEY (id_servicio)
		REFERENCES SERVICIO (id_servicio)
);
