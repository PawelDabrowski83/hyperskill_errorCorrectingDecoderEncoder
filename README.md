# hyperskill_errorCorrectingDecoderEncoder

Stage 1/5: Symbol-level error emulator 
<p>In this stage, you should write a program that creates errors in the input text, 1 random error per 3 symbols. An error means that the character is replaced by another random character. For example, “abc” characters can be “*bc” or “a*c” or “ab*”, where * is a random character. You can replace by any character but recommended to use only uppercase and lowercase English letters, spacebar and numbers.</p>

Stage 2/5: Symbol-level correction code 
<p>In this stage, you should write a program that:
   
       Takes a message the user wants it send. The input contains a single message.
       Encode the message by tripling all the symbols.
       Simulate sending this message via a poor internet connection (in other words, simulate errors).
       Decode it back again.
   
   Output the message on every step!</p>
   
Stage 3/5: Bit-level error emulator
<p>In this stage, you should write a program that reads the text the user wants to send from the send.txt, and simulate the sending through a poor internet connection making one-bit errors in every byte of the text. Notice that this text is no longer a string since after manipulations in every byte it may happen to be that some bytes didn't correspond to a specific character in the table because Java does not use ASCII table representation in their String implementation. Java uses UNICODE that happens to match with ASCII in the first 128 symbols, but no further. The String class is too complicated for low-level manipulations so you should use bytes or chars instead.

The received message which contains an error in a single bit in every byte should be saved into received.txt.</p>

Stage 4/5: Bit-level correction code
<p>The program in this stage should work in 3 modes: encode, send and decode.
   
   If the mode is encode then you need to take the text from send.txt, convert it to ready-to-send form (where you have three significant bits per byte) and save the resulted bytes into the file named encoded.txt.
   
   If the mode is send, you should take the file from encoded.txt and simulate the errors in its bytes (1 bit per byte) and save the resulted bytes into the file named received.txt.
   
   If the mode is decode, you should take the file from received.txt and decode it to the normal text. Save the text into the file named decoded.txt.</p>