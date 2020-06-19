package example.typechecker;

import example.parser.Type;

// description of an operator, at the type level
public class OpDesc {
    // ---BEGIN INSTANCE VARIABLES---
    public final Type expectedLeft;
    public final Type expectedRight;
    public final Type returnType;
    // ---END INSTANCE VARIABLES---

    public OpDesc(final Type expectedLeft,
                  final Type expectedRight,
                  final Type returnType) {
        this.expectedLeft = expectedLeft;
        this.expectedRight = expectedRight;
        this.returnType = returnType;
    } // OpDesc

    @Override
    public String toString() {
        return ("OpDesc(" +
                expectedLeft.toString() +
                ", " +
                expectedRight.toString() +
                ", " +
                returnType.toString() +
                ")");
    } // toString

    @Override
    public boolean equals(final Object other) {
        if (other instanceof OpDesc) {
            final OpDesc otherDesc = (OpDesc)other;
            return (expectedLeft.equals(otherDesc.expectedLeft) &&
                    expectedRight.equals(otherDesc.expectedRight) &&
                    returnType.equals(otherDesc.returnType));
        } else {
            return false;
        }
    } // equals

    @Override
    public int hashCode() {
        return (expectedLeft.hashCode() +
                expectedRight.hashCode() +
                returnType.hashCode());
    } // hashCode
} // OpDesc
