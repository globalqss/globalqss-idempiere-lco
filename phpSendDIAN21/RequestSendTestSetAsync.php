<?php
// Example:
// php RequestSendTestSetAsync.php 1000000 2 sendTestSetAsync_face_f0830085359003b02376f /tmp/ComprobantesFirmados/ws_f0830085359003b02376d.zip
require 'vendor/autoload.php';
date_default_timezone_set('America/Bogota');
use Stenfrank\UBL21dian\Client;
use Stenfrank\UBL21dian\Templates\SOAP\SendTestSetAsync;

$clientID = $argv[1];
$env = $argv[2];
$home_path_var = $_SERVER["HOME"];
$ini = parse_ini_file($home_path_var . '/.CertDIAN21_' . $clientID . '.ini');
$pathCertificate = $ini['pathCert'];
$passwors = $ini['passCert'];
$baseOutFile = $argv[3];

$sendTestSetAsync = new SendTestSetAsync($pathCertificate, $passwors);
if ($env == "1") {
    $sendTestSetAsync->To = 'https://vpfe.dian.gov.co/WcfDianCustomerServices.svc?wsdl';
}
$sendTestSetAsync->fileName = basename($argv[4]);

$zip_file = $argv[4];
$bin = file_get_contents($zip_file);
$b64 = base64_encode($bin);
$sendTestSetAsync->contentFile = $b64;
$sendTestSetAsync->testSetId = $ini['testSetId'];
$sendTestSetAsync->sign();

$client = new Client($sendTestSetAsync);

file_put_contents($baseOutFile . '_request.xml', $sendTestSetAsync->xml);
file_put_contents($baseOutFile . '_answer.xml', $client->getResponse());
print("OK\n");
?>
