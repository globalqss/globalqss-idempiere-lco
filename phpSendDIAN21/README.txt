CREDITS: Kudos para Frank Aguirre https://github.com/Stenfrank/soap-dian

Requisitos, php y extensiones en ubuntu, probado con:
php7.2-cli
php7.2-xml
php7.2-mbstring
php7.2-curl
(debería funcionar similar en windows)

Se debe configurar un archivo $HOME/.CertDIAN21_[clientId].ini
con las variables
pathCert -> ruta al certificado del cliente con ID clientId
passCert -> password del certificado del cliente con ID clientId
testSetId -> si se requiere usar RequestSendTestSetAsync

Por ejemplo el archivo /home/carlos/.CertDIAN21_1000000.ini podría contener:
pathCert = "/opt/CertificadoQSS.pfx"
passCert = "mipripass"
testSetId= "40e8ab1f-c024-46ff-b1a7-8c568f14efdc"


Para ejecutar consta de 4 scripts:
RequestGetStatus.php [clientId] [ambiente] [basename_output_file] [trackId]
Ej:
php RequestGetStatus.php 1000000 2 /tmp/ComprobantesFirmados/getStatus_face_f0830085359003b02376f 553bc48851ed5ba685ec46064ad608e5d16ed562a4d262904679882f49a6d6b2861e6298f8a9da3a232875cc177ba1a5

RequestSendBillAsync.php [clientId] [ambiente] [basename_output_file] [zip_file]
php RequestSendBillAsync.php 1000000 2 /tmp/ComprobantesFirmados/sendBillAsync_face_f0830085359003b02376f /tmp/ComprobantesFirmados/ws_f0830085359003b02376d.zip 

RequestSendBillSync.php [clientId] [ambiente] [basename_output_file] [zip_file]
php RequestSendBillSync.php 1000000 2 /tmp/ComprobantesFirmados/sendBillSync_face_f0830085359003b02376f /tmp/ComprobantesFirmados/ws_f0830085359003b02376d.zip 

RequestSendTestSetAsync.php [clientId] [ambiente] [basename_output_file] [zip_file]
php RequestSendTestSetAsync.php 1000000 2 /tmp/ComprobantesFirmados/sendTestSetAsync_face_f0830085359003b02376f /tmp/ComprobantesFirmados/ws_f0830085359003b02376d.zip


Todos los scripts dejan dos archivos de salida:
[basename_output_file]_request.xml  -> con la solicitud que se envió a la DIAN
[basename_output_file]_answer.xml   -> con la respuesta recibida por la DIAN
