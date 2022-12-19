import { Button, Card, Typography } from "antd";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { surveyClient } from "../client/surveyClient";
import { ProgramCard } from "../components/ProgramCard";
import { Program } from "../types";
import { None } from "../utils/None";
import "../Survey.css";

export function AllPrograms() {
  const [allPrograms, setAllPrograms] = useState<Program[]>([]);

  // This state will be used to tell if a new session start is underway
  const [isLoading, setIsLoading] = useState(false);

  // We'll need to navigate to the survey page
  const navigate = useNavigate();

  const sessionId = new URLSearchParams(window.location.search).get("sessionId");
  if (None(sessionId) || sessionId === "") {
    navigate("/");
  }

  useEffect(() => {
    const getAllPrograms = async () => {
      if (sessionId != null) {
        const allPrograms = await surveyClient.getAllPrograms(sessionId);
        setAllPrograms(allPrograms);
      } else {
        navigate("/");
      }
    };
    getAllPrograms();
  }, [allPrograms.length]);

  return (
    <>
      {allPrograms.length > 0 && (
        <div>
          <div id="summary-title-id" className="question-title-style">
            The following is the list of all programs that we are currently tracking. We update this list often so come
            back often to find what programs you might be eligible for.
          </div>
          {allPrograms
            .filter((p) => p != null)
            .map((p) => (
              <ProgramCard key={p.id} program={p} />
            ))}
        </div>
      )}
      {allPrograms.length === 0 && (
        <div className="card-style">
          We couldn't find any matching programs for your location just yet. We will contact you if we find one in the
          future.
          <div>If you would like to start over, click below:</div>
          <Button id="program-card-button" className="program-card-button-style" href="/">
            Start over
          </Button>
        </div>
      )}
    </>
  );
}
