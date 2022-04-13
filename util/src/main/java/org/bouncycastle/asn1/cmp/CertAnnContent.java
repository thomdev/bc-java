package org.bouncycastle.asn1.cmp;

import java.io.IOException;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.x509.AttributeCertificate;
import org.bouncycastle.asn1.x509.Certificate;

/**
 *  CertAnnContent ::= CMPCertificate
 */
public class CertAnnContent
    extends CMPCertificate
{

    public CertAnnContent(AttributeCertificate x509v2AttrCert)
    {
        super(x509v2AttrCert);
    }

    public CertAnnContent(int type, ASN1Object otherCert)
    {
        super(type, otherCert);
    }

    public CertAnnContent(Certificate x509v3PKCert)
    {
        super(x509v3PKCert);
    }

    public static CertAnnContent getInstance(ASN1TaggedObject ato, boolean isExplicit)
    {
        if (ato != null)
        {
            if (isExplicit)
            {
                return CertAnnContent.getInstance(ato.getObject());
            }
            else
            {
                throw new IllegalArgumentException("tag must be explicit");
            }
        }
        return null;
    }

    public static CertAnnContent getInstance(Object o)
    {
        if (o == null || o instanceof CertAnnContent)
        {
            return (CertAnnContent)o;
        }

        if (o instanceof CMPCertificate)
        {
            try
            {
                return CertAnnContent.getInstance(((CMPCertificate)o).getEncoded());
            }
            catch (IOException e)
            {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        if (o instanceof byte[])
        {
            try
            {
                o = ASN1Primitive.fromByteArray((byte[])o);
            }
            catch (IOException e)
            {
                throw new IllegalArgumentException("Invalid encoding in CMPCertificate");
            }
        }

        if (o instanceof ASN1Sequence)
        {
            return new CertAnnContent(Certificate.getInstance(o));
        }

        if (o instanceof ASN1TaggedObject)
        {
            ASN1TaggedObject taggedObject = (ASN1TaggedObject)o;

            return new CertAnnContent(taggedObject.getTagNo(), taggedObject.getObject());
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }


}
