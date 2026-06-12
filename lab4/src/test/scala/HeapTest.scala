
import heap._
import Heap.Operation
import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class HeapTest extends AnyFlatSpec with ChiselScalatestTester {

  behavior of "Heap"

  it should "present the currently largest number on io.root while ready and not empty" in {
    test(new TestHeap) { dut =>

      // setup a new operation
      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(220.U)
      dut.io.valid.poke(1.B)

      // step to start operation
      dut.clock.step()

      // the operation has been initiated and we can deassert valid
      dut.io.valid.poke(0.B)

      // wait for the dut to get ready again
      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      // the inserted value should appear as the largest value for now
      dut.io.root.expect(220.U)

      // write more test code here
      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(20.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      // wait for the dut to get ready again
      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.root.expect(220.U)

      dut.io.op.poke(Operation.RemoveRoot)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      // the operation has been initiated and we can deassert valid
      dut.io.valid.poke(0.B)

      // wait for the dut to get ready again
      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.root.expect(20.U)
    }
  }

  it should "not change the stored values" in {
    // Insert a series of values and remove the root until 
    // the heap is empty while saving the root values. 
    // The set of saved values should be the same as the 
    // set of inserted values.
    test(new TestHeap) { dut =>
      // setup a new operation
      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()


      // setup a new operation
      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(2.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      // setup a new operation
      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(3.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      // setup a new operation
      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(4.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      // REMOVING
      dut.io.root.expect(4.U)

      dut.io.op.poke(Operation.RemoveRoot)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      //NEXT
      dut.io.root.expect(3.U)

      dut.io.op.poke(Operation.RemoveRoot)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()
      
      //NEXT
      dut.io.root.expect(2.U)

      dut.io.op.poke(Operation.RemoveRoot)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()
      
      //NEXT
      dut.io.root.expect(1.U)

      dut.io.op.poke(Operation.RemoveRoot)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()
    }
  }

  it should "assert empty after all numbers have been removed" in {
    test(new TestHeap) { dut =>
      // setup a new operation
      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.empty.expect(false)
      
      dut.io.op.poke(Operation.RemoveRoot)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.empty.expect(true)
    }
  }

  it should "assert full when 8 numbers have been inserted" in {
    test(new TestHeap) { dut =>
      dut.io.full.expect(false)

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.full.expect(false)

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.full.expect(true)
    }
  }

  it should "deassert full after one number is removed when it was full" in {
    test(new TestHeap) { dut =>
      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.RemoveRoot)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()   

      dut.io.full.expect(false)
    }
  }

  it should "not change the sequence if new insertions are issued when it is full" in {
    test(new TestHeap) { dut =>
      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(1.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(10.U)
      dut.io.valid.poke(1.B)

      dut.clock.step()

      dut.io.valid.poke(0.B)

      while (!dut.io.ready.peekBoolean()) dut.clock.step()

      dut.io.root.expect(1.U)
    }
  }

}
