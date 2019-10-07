package com.openbravo.data.loader;

public class QBFBuilderCustom extends QBFBuilder implements ISQLBuilderStatic {
    public QBFBuilderCustom(String sSentence, String[] asFindFields) {
        super(sSentence, asFindFields);
        int iPos = sSentence.indexOf(", BARCODE_TABLE WHERE ?(QBF_FILTER)");
        if (iPos < 0) {
            m_sSentBeginPart = sSentence;
            m_sSentEndPart = "";
            m_sSentNullFilter = sSentence;
        } else {
            m_sSentBeginPart = sSentence.substring(0, iPos + 21);
            m_sSentEndPart = sSentence.substring(iPos + 35);
            m_sSentNullFilter = sSentence.substring(0, iPos) + " WHERE (1=1)" + sSentence.substring(iPos + 35);
        }
        m_asFindFields = asFindFields;
    }
}
