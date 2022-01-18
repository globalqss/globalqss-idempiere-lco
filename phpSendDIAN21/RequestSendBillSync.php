<?php
// Example:
// php RequestSendBillSync.php 1000000 2 sendBillSync_face_f0830085359003b02376f /tmp/ComprobantesFirmados/ws_f0830085359003b02376d.zip 
require 'vendor/autoload.php';
date_default_timezone_set('America/Bogota');
use Stenfrank\UBL21dian\Client;
use Stenfrank\UBL21dian\Templates\SOAP\SendBillSync;

$clientID = $argv[1];
$env = $argv[2];
$home_path_var = $_SERVER["HOME"];
$ini = parse_ini_file($home_path_var . '/.CertDIAN21_' . $clientID . '.ini');
$pathCertificate = $ini['pathCert'];
$passwors = $ini['passCert'];
$baseOutFile = $argv[3];

$sendBillSync = new SendBillSync($pathCertificate, $passwors);
if ($env == "1") {
    $sendBillSync->To = 'https://vpfe.dian.gov.co/WcfDianCustomerServices.svc?wsdl';
}
$sendBillSync->fileName = basename($argv[4]);

$zip_file = $argv[4];
$bin = file_get_contents($zip_file);
$b64 = base64_encode($bin);
$sendBillSync->contentFile = $b64;
$sendBillSync->sign();

$client = new Client($sendBillSync);

file_put_contents($baseOutFile . '_request.xml', $sendBillSync->xml);
file_put_contents($baseOutFile . '_answer.xml', $client->getResponse());
print("OK\n");
?>
