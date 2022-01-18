<?php

namespace Stenfrank\UBL21dian\Templates\SOAP;

use Stenfrank\UBL21dian\Templates\Template;
use Stenfrank\UBL21dian\Templates\CreateTemplate;

/**
 * Get numbering range.
 */
class GetNumberingRange extends Template implements CreateTemplate
{
    /**
     * Action.
     *
     * @var string
     */
    public $Action = 'http://wcf.dian.colombia/IWcfDianCustomerServices/GetNumberingRange';

    /**
     * Required properties.
     *
     * @var array
     */
    protected $requiredProperties = [
        'accountCode',
        'accountCodeT',
        'softwareCode',
    ];

    /**
     * Construct.
     *
     * @param string $pathCertificate
     * @param string $passwors
     */
    public function __construct($pathCertificate, $passwors)
    {
        parent::__construct($pathCertificate, $passwors);
    }

    /**
     * Create template.
     *
     * @return string
     */
    public function createTemplate()
    {
        return $this->templateXMLSOAP = <<<XML
<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:wcf="http://wcf.dian.colombia">
    <soap:Body>
        <wcf:GetNumberingRange>
            <!--Optional:-->
            <wcf:accountCode>{$this->accountCode}</wcf:accountCode>
            <!--Optional:-->
            <wcf:accountCodeT>{$this->accountCodeT}</wcf:accountCodeT>
            <!--Optional:-->
            <wcf:softwareCode>{$this->softwareCode}</wcf:softwareCode>
        </wcf:GetNumberingRange>
    </soap:Body>
</soap:Envelope>
XML;
    }
}
