package org.jbei.ice.lib.parsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.jbei.ice.lib.vo.IDNASequence;

import org.apache.commons.io.IOUtils;

/**
 * This class provides skeletal implementation of {@link IDNAParser} interface.
 *
 * @author Zinovii Dmytriv, Timothy Ham
 */
public abstract class AbstractParser implements IDNAParser {

    @Override
    public abstract IDNASequence parse(String textSequence) throws InvalidFormatParserException;

    @Override
    public abstract IDNASequence parse(byte[] bytes) throws InvalidFormatParserException;

    @Override
    public IDNASequence parse(File file) throws IOException, InvalidFormatParserException {
        byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
        return parse(bytes);
    }

    /**
     * Replace different line termination characters with the newline character (\n).
     *
     * @param sequence Text to clean.
     * @return String with only newline character (\n).
     */
    protected String cleanSequence(String sequence) {
        sequence = sequence.trim();
        sequence = sequence.replace("\n\n", "\n"); // *nix
        sequence = sequence.replace("\n\r\n\r", "\n"); // win
        sequence = sequence.replace("\r\r", "\n"); // mac
        sequence = sequence.replace("\n\r", "\n"); // *win
        return sequence;
    }
}
