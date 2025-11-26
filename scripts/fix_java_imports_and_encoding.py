#!/usr/bin/env python3
"""
Script to fix a few common issues in the repo: 
- Replace Unicode replacement character sequences used as color codes ("�3") with the Java unicode escape for section sign ("\u00A73").
- Replace wildcard java.awt.* imports with java.awt.Color when Color is used (or remove if not used).
- Fully qualify com.google.common.base.Optional usages to avoid ambiguity with java.util.Optional.

Run from the repo root: `python3 scripts/fix_java_imports_and_encoding.py`
This script modifies files in-place and prints a summary.
"""
import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
JAVA_SRC = ROOT / 'src' / 'main' / 'java'

def replace_encoding_tokens(text: str) -> str:
    # Replace the Unicode replacement char followed by 3 with Java unicode escape
    # Example: "�3Attached to" -> "\\u00A73Attached to"
    text = text.replace('\uFFFD3', '\\u00A73')  # explicit replacement if file contains U+FFFD
    # Some files may literally contain '�' (encoded). Use the literal char as well
    text = text.replace('�3', '\\u00A73')
    # also handle sequences like '�33x' -> '\\u00A733x' (just replace the initial �3)
    return text

def fix_awt_imports_and_color(text: str) -> str:
    # If java.awt.* imported, and only Color is used, replace the import.
    if 'import java.awt.*;' in text:
        uses_color = 'new Color(' in text or 'Color ' in text
        if uses_color:
            text = re.sub(r'import\s+java\.awt\.\*;', 'import java.awt.Color;', text)
        else:
            # remove the import completely
            text = re.sub(r'import\s+java\.awt\.\*;\n', '', text)
    return text

def qualify_guava_optional(text: str) -> str:
    # Only qualify Optional when com.google.common.base.Optional is expected.
    # We'll change type declarations like 'Optional<' to 'com.google.common.base.Optional<'
    # and change static call sites 'Optional.of(' and 'Optional.absent(' etc.
    patterns = [
        (r'(?<![\w.])Optional<', 'com.google.common.base.Optional<'),
        (r'(?<![\w.])Optional\s*\(', 'com.google.common.base.Optional('),
        (r'(?<![\w.])Optional\.', 'com.google.common.base.Optional.'),
        (r'\(Optional\)', '(com.google.common.base.Optional)'),
    ]
    for pat, repl in patterns:
        text = re.sub(pat, repl, text)
    return text

def should_process_file(path: Path) -> bool:
    if path.suffix != '.java':
        return False
    return True

def process_file(path: Path) -> bool:
    changed = False
    text = path.read_text(encoding='utf-8', errors='replace')
    new_text = text
    # Replace weird encoding markers
    new_text = replace_encoding_tokens(new_text)
    # Replace awt.* import with Color or remove
    new_text = fix_awt_imports_and_color(new_text)
    # Only attempt to qualify Optional if both imports are present
    if 'import com.google.common.base.*;' in new_text and 'import java.util.*;' in new_text:
        if 'Optional<' in new_text or 'Optional.' in new_text or '(Optional)' in new_text:
            new_text = qualify_guava_optional(new_text)
    # Make sure we didn't accidentally break the character encoding for the section sign: Java unicode escape
    if new_text != text:
        path.write_text(new_text, encoding='utf-8')
        changed = True
    return changed

def main():
    src_files = list(JAVA_SRC.rglob('*.java'))
    modified_files = []
    for f in src_files:
        if should_process_file(f):
            try:
                did = process_file(f)
                if did:
                    print('Modified:', f)
                    modified_files.append(f)
            except Exception as e:
                print('Failed to process', f, e)
    print('\nSummary: modified %d files' % len(modified_files))

if __name__ == '__main__':
    main()
