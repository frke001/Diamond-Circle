package org.unibl.etf.figures;

import org.unibl.etf.Color;

public class SuperFastFigure extends Figure {
	
	public SuperFastFigure(Color figureColor) {
		super(figureColor);
	}
	@Override
	public String toString() {
		return name + " (super brza, " + this.figureColor + ")" + super.toString();
	}
}
