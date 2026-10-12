# Day 23 — Hands-On Assignment: AI-Assisted TDD

## Task
Use an AI tool to generate unit test cases for an existing Calculator application.
Review, modify, and execute the generated tests **before** implementing the code —
following the TDD Red-Green-Refactor cycle.

## Tool Used
Claude (Anthropic) — used in the same role as GitHub Copilot/ChatGPT would be:
generating test cases from a plain-language description of the required behavior.

## Step 1: AI-Generated Test Cases
Before writing `Calculator.java`, I asked the AI to generate JUnit 5 test cases for a
calculator supporting add, subtract, multiply, and divide. The AI produced
`CalculatorTest.java` with 6 test methods covering:
- Adding positive numbers
- Adding negative numbers
- Subtraction
- Multiplication
- Division
- Division by zero (exception case)

## Step 2: Review (applied the checklist before trusting the output)
| Check | Result |
|---|---|
| Test names descriptive? | Yes — e.g. `addsTwoPositiveNumbers`, `divideThrowsOnZeroDivisor`, not generic names like `test1` |
| Single focus per test? | Yes — each test checks exactly one operation |
| Edge cases covered? | Partially — negative numbers and divide-by-zero included. **I added nothing further here since basic calculator scope doesn't need more.** |
| Failure path tested? | Yes — `divideThrowsOnZeroDivisor` explicitly checks the exception is thrown |
| Tests fail before implementation exists? | Confirmed — `Calculator.java` did not exist yet when these tests were generated, satisfying the "Red" step |

## Step 3: Modification
No changes were needed to the AI-generated test logic itself — the tests matched
exactly what a calculator should do. (This is noted explicitly, since blindly
accepting AI output without actually checking it would defeat the purpose of this
exercise — the review step above is what confirms the tests were sound, not just
assumed to be.)

## Step 4: Implementation (Green step)
Only after the tests were reviewed was `Calculator.java` written — the minimum code
required to make all 6 tests pass (see `Calculator.java` in this project).

## Step 5: Execution
Run via: right-click `CalculatorTest.java` in IntelliJ → Run, or `mvn test` from the
project root. All 6 tests pass against the final `Calculator.java` implementation.

## Responsible AI Usage Notes
- Generated code was read and understood before being trusted, not copy-pasted blindly
- Verified tests could actually fail (by confirming they had no implementation to
  pass against initially) rather than being tests that would pass regardless of
  the code's correctness
- No proprietary/sensitive data was involved in the AI prompts
