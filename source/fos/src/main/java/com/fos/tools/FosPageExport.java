package com.fos.tools;

import javax.servlet.http.HttpServletRequest;

public abstract class FosPageExport extends FosPage {

    /**
     * Erstellt eine neue Fos Seite welche das Login überprüft
     *
     * @param request         der request vom jsp
     * @param needsAdminRight Angabe, ob Administratorenrechte für diese Seite benötigt werden.
     */
    public FosPageExport(HttpServletRequest request, Boolean needsAdminRight) {
        super(request, needsAdminRight);
    }

    public abstract String getExport();
}
