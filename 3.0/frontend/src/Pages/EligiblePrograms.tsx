import { Button } from "antd";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { surveyClient } from "../client/surveyClient";
import { ProgramCard } from "../components/ProgramCard";
import { Program } from "../types";
import { None } from "../utils/None";
import "../Survey.css";

export function EligiblePrograms() {
  const [eligiblePrograms, setEligiblePrograms] = useState<Program[]>([]);
  const [allProgramsUrl, setAllProgramsUrl] = useState<string>("");

  // We'll need to navigate to the survey page
  const navigate = useNavigate();

  const sessionId = new URLSearchParams(window.location.search).get("sessionId");
  if (None(sessionId) || sessionId === "") {
    navigate("/");
  }

  useEffect(() => {
    const getEligiblePrograms = async () => {
      if (sessionId != null) {
        const eligiblePrograms = await surveyClient.getEligiblePrograms(sessionId);
        setEligiblePrograms(eligiblePrograms);
        setAllProgramsUrl("/allprograms?sessionId=" + sessionId);
      } else {
        navigate("/");
      }
    };
    getEligiblePrograms();
  }, [eligiblePrograms.length]);

  return (
    <>
      {eligiblePrograms.length > 0 && (
        <div>
          <div id="summary-title-id" className="question-title-style">
            Congratulations! Based on your answers, you may be eligible for {eligiblePrograms.length} programs. Click
            the <b>Visit Website</b> button to learn more and to apply.
          </div>
          {eligiblePrograms
            .filter((p) => p != null)
            .map((p) => (
              <ProgramCard key={p.id} program={p} />
            ))}
        </div>
      )}
      {eligiblePrograms.length === 0 && (
        <div className="card-style">
          <h1>Sorry...</h1>
          <div>
            Based on your answers, we were not able to find an eligible program for you. If you provided your email
            address, we will contact you if we find one in the future.
          </div>
          <br></br>
          <div>To view a full list of programs, click below:</div>
          <Button id="program-card-button" className="program-card-button-style" href={allProgramsUrl} target="_blank">
            View All Programs
          </Button>
        </div>
      )}
    </>
  );
}
