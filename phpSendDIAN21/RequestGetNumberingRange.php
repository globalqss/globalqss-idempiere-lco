<?php
// Example:
// php RequestGetNumberingRange.php 1000000 2 getNumberingRange_202109021805 830085359 830085359 1782c594-0f4d-42c6-bb08-6c80bb8ccbe7
require 'vendor/autoload.php';
date_default_timezone_set('America/Bogota');
use Stenfrank\UBL21dian\Client;
use Stenfrank\UBL21dian\Templates\SOAP\GetNumberingRange;

$clientID = $argv[1];
$env = $argv[2];
$home_path_var = $_SERVER["HOME"];
$ini = parse_ini_file($home_path_var . '/.CertDIAN21_' . $clientID . '.ini');
$pathCertificate = $ini['pathCert'];
$passwors = $ini['passCert'];
$baseOutFile = $argv[3];

$getNumberingRange = new GetNumberingRange($pathCertificate, $passwors);
if ($env == "1") {
    $getNumberingRange->To = 'https://vpfe.dian.gov.co/WcfDianCustomerServices.svc?wsdl';
}
$getNumberingRange->accountCode = $argv[4];
$getNumberingRange->accountCodeT = $argv[5];
$getNumberingRange->softwareCode = $argv[6];
$getNumberingRange->sign();

$client = new Client($getNumberingRange);

file_put_contents($baseOutFile . '_request.xml', $getNumberingRange->xml);
file_put_contents($baseOutFile . '_answer.xml', $client->getResponse());
print("OK\n");
?>
