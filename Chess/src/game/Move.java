// package game;
//
// public class Move {
// private final int[] from, to;
//
// private final int kingPiece;
// private final boolean kingSide;
//
// /**
// * @param move
// * is fx, fy, tx, ty
// */
// public Move(int... move) {
// from = new int[2];
// to = new int[2];
// from[0] = move[0];
// from[1] = move[1];
// to[0] = move[2];
// to[1] = move[3];
//
// // not a castle
// kingPiece = Piece.NONE;
// kingSide = false;
// }
//
// public Move(int[] from, int[] to) {
// if (from.length != 2 || to.length != 2)
// throw new RuntimeException("from or to are null or not proper 2 length
// format");
// this.from = from;
// this.to = to;
//
// // not a castle
// kingPiece = Piece.NONE;
// kingSide = false;
// }
//
// /**
// * Use this constructor for constructing a castle move;
// *
// * @param kingPiece
// * @param kingSide
// * should be true if castling toward kingside, false if queenside
// */
// public Move(int kingPiece, boolean kingSide) {
//
// // not a normal movement
// from = to = null;
//
// this.kingPiece = Piece.simplePosNeg(kingPiece) * Piece.WK;
// this.kingSide = kingSide;
//
// }
//
// public boolean isCastle() {
// return kingPiece != Piece.NONE;
// }
//
// public int getKing() {
// return kingPiece;
// }
//
// public boolean castleSide() {
// return kingSide;
// }
//
// public Move[] getRequiredMovesForCastle() {
// if (!isCastle())
// throw new RuntimeException("Not castle move");
// int y = kingPiece == Piece.WK ? 0 : Board.HEIGHT - 1;
// int xr, dxr, dxk;
// if (kingSide) {
// xr = Board.WIDTH - 1;
// dxr = -2;
// dxk = 2;
// } else {
// xr = 0;
// dxr = 3;
// dxk = -2;
// }
// return new Move[] { new Move(4, y, 4 + dxk, y), new Move(xr, y, xr + dxr, y)
// };
//
// }
//
// public int[] getMoveCoor() {
// return new int[] { from[0], from[1], to[0], to[1] };
// }
//
// public int[] getFrom() {
// return from;
// }
//
// public int fx() {
// return from[0];
// }
//
// public int fy() {
// return from[1];
// }
//
// public int[] getTo() {
// return to;
// }
//
// public int tx() {
// return to[0];
// }
//
// public int ty() {
// return to[1];
// }
//
// @Override
// public String toString() {
// return "Move: " + from[0] + "," + from[1] + " to " + to[0] + "," + to[1];
// }
//
// // TODO keep or get rid of this method equals
// @Override
// public boolean equals(Object another) {
// Move m = (Move) another;
// return (fx() == m.fx() && fy() == m.fy() && tx() == m.tx() && ty() ==
// m.ty());
// }
//
// }
