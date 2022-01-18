<?php
// Example:
// php CallSignInvoice.php 1000000 INVOIC /tmp/ComprobantesGenerados/face_f0830085359003b02383a.xml /tmp/ComprobantesFirmados/face_f0830085359003b02383a_signed.xml
require 'vendor/autoload.php';
date_default_timezone_set('America/Bogota');
use Stenfrank\UBL21dian\XAdES\SignInvoice;
use Stenfrank\UBL21dian\XAdES\SignDebitNote;
use Stenfrank\UBL21dian\XAdES\SignCreditNote;

$clientID = $argv[1];
$dianShortDocType = $argv[2];
$fileToSign = $argv[3];
$fileSigned = $argv[4];
$home_path_var = $_SERVER["HOME"];
$ini = parse_ini_file($home_path_var . '/.CertDIAN21_' . $clientID . '.ini');
$pathCertificate = $ini['pathCert'];
$passwors = $ini['passCert'];

$domDocument = new DOMDocument();
$domDocument->load($fileToSign);

if ($dianShortDocType == 'ND') {
    $signInvoice = new SignDebitNote($pathCertificate, $passwors, $domDocument->saveXML(), SignInvoice::ALGO_SHA256);
} elseif ($dianShortDocType == 'NC') {
    $signInvoice = new SignCreditNote($pathCertificate, $passwors, $domDocument->saveXML(), SignInvoice::ALGO_SHA256);
} else {
    $signInvoice = new SignInvoice($pathCertificate, $passwors, $domDocument->saveXML(), SignInvoice::ALGO_SHA256);
}
// $signInvoice->softwareID = 'xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx';  // AD_OrgInfo.LCO_FE_UserName
// $signInvoice->pin = '99999';   // AD_OrgInfo.LCO_FE_UserPass
// $signInvoice->technicalKey = 'xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx'; // LCO_PrintedFormControl.TechKey

file_put_contents($fileSigned, $signInvoice->xml);
print("OK\n");
?>
