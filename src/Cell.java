public class Cell {
	// height would be Y axis, width would be X axis
	// Row would Y axis, column would be X axis
		int x;
		int y;
		char c;

		public Cell(int x, int y, char c) {
			this.x = x;
			this.y = y;
			this.c = c;
		}

		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		public char getC() {
			return c;
		}

		public void setX(int x) {
			this.x = x;
		}
		public void setY(int y) {
			this.y = y;
		}
		public void setC(char c) {
			this.c = c;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Cell))
				return false;
			Cell o = (Cell) obj;
			return this.x == o.getX() && this.y == o.getY()&& this.c == o.getC();
		}

		@Override
		public String toString() {
			return String.format("(%s,%s,%c)", x, y,c);
		}
		
		public void printCell(){
			System.out.println(toString());	
		}

}
