package com.example.huffmanencoding.Controllers;

import com.example.huffmanencoding.Encoding.HuffmanEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HuffmanEncoding {
    @GetMapping("/huffmanEncoding")
    public String endcode(@RequestParam(name = "text") String text, Model model){

        HuffmanEncoder encoder = new HuffmanEncoder(text);
        encoder.encode();

        model.addAttribute("json", encoder.getVisualization());

        model.addAttribute("text", text);

        model.addAttribute("tableData", encoder.getTableData());

        model.addAttribute("originalBitCount", encoder.getOriginalBitCount());
        model.addAttribute("savingBitCount", encoder.getEncodedMessageBitCount());
        model.addAttribute("savings", encoder.getSavings());

        return "encoding";
    }
}
