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