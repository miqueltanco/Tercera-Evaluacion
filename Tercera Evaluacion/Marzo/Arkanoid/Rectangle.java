package Arkanoid;

class Rectangle extends GameObject {

		double x, y;
		double sizeX;
		double sizeY;

		double left() {
			return x - sizeX / 2.0;
		}

		double right() {		
			return x + sizeX / 2.0;
		}

		double top() {
			return y - sizeY / 2.0;
		}

		double bottom() {
			return y + sizeY / 2.0;
		}

	}