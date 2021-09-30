-- 2.1 Anexo 1.8

update LCO_FE_OperationType set IsActive='N' where LCO_FE_OperationType_ID=1000034;
-- 23 - Nota Crédito para facturación electrónica V1 (Decreto 2242)
update LCO_FE_OperationType set IsActive='N' where LCO_FE_OperationType_ID=1000037;
-- 33 - Nota Débito para facturación electrónica V1 (Decreto 2242)

/*
Se actualiza el valor a enviar para la versión del formato del documento (ProfileID) de acuerdo con tabla 40:
DIAN 2.1: Factura Electrónica de Venta
DIAN 2.1: Nota Crédito de Factura Electrónica de Venta
DIAN 2.1: Nota Débito de Factura Electrónica de Venta
-- N/A DIAN 2.1: Factura Electrónica de Venta Exportación -- Regla: FAD03, Rechazo: ProfileID : no contiene el literal
*/

update LCO_FE_DIAN_Format set Name='Factura Electrónica de Venta' where LCO_FE_DIAN_Format_ID=1000006; -- FV21
update LCO_FE_DIAN_Format set Name='Factura Electrónica de Venta' where LCO_FE_DIAN_Format_ID=1000004; -- FE21
update LCO_FE_DIAN_Format set Name='Nota Crédito de Factura Electrónica de Venta' where LCO_FE_DIAN_Format_ID=1000001; -- NC21
update LCO_FE_DIAN_Format set Name='Nota Débito de Factura Electrónica de Venta' where LCO_FE_DIAN_Format_ID=1000002; -- ND21

update C_UOM set X12DE355 = 'HUR' where C_UOM_ID=101; -- 'HR'

-- begin work;
update LCO_FE_TributaryType set IsActive = 'N';
update LCO_FE_TributaryType set IsActive = 'Y' where value in
 ('O-13', 'O-15', 'O-23', 'O-47', 'R-99-PN'
);
-- commit;

-- begin work;
update LCO_FE_BP_Info set IsActive = 'N';
update LCO_FE_BP_Info set IsActive = 'Y' where LCO_FE_TributaryType_ID in
 (select LCO_FE_TributaryType_ID from LCO_FE_TributaryType where IsActive = 'Y');
-- commit

